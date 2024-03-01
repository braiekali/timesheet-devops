package tn.esprit.ProjetSpring.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Exception.UserAlreadyExistsException;
import tn.esprit.ProjetSpring.Repositories.PasswordResetTokenRepository;
import tn.esprit.ProjetSpring.Repositories.RoleRepository;
import tn.esprit.ProjetSpring.Repositories.UserRepository;
import tn.esprit.ProjetSpring.Repositories.VerificationTokenRepository;
import tn.esprit.ProjetSpring.dto.RegistrationRequest;
import tn.esprit.ProjetSpring.entities.PasswordResetToken;
import tn.esprit.ProjetSpring.entities.Role;
import tn.esprit.ProjetSpring.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.ProjetSpring.entities.VerificationToken;


import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{


    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired

    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    PasswordResetTokenRepository passwordResetTokenRepository ;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired

    VerificationTokenRepository verificationTokenRepository ;



    @Override
    public User addUser(User user, MultipartFile imageFile) {
        String imageUrl = fileStorageService.storeFile(imageFile);
        user.setImageUrl(imageUrl);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActive(false);
        Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(role);

        return userRepository.save(user);
    }
    public User handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        User user = userRepository.findById(id).orElse(null);
        user.setImageUrl(fileName);
        return userRepository.save(user);
    }

    @Override
    public User getUser(long idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            VerificationToken token = verificationTokenRepository.findByUser(user);

            if (token != null) {
                verificationTokenRepository.delete(token);
            }

            userRepository.delete(user);
        }
    }




    @Override
    public User updateUser(User user, MultipartFile imageFile) {
        User existingUser = userRepository.findById(user.getIdUser()).orElse(null);

        if (existingUser != null) {
            // Check if a new image file is provided
            if (imageFile != null && !imageFile.isEmpty()) {
                // Store the new image file and update the imageUrl
                String imageUrl = fileStorageService.storeFile(imageFile);
                existingUser.setImageUrl(imageUrl);
            }

            // Update other fields
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setCin(user.getCin());
            existingUser.setPhone(user.getPhone());
            existingUser.setPassword(user.getPassword());


            // Update other fields as needed

            userRepository.save(existingUser);
        }

        return existingUser;
    }

    @Override
    public Optional<User> findByCin(long cin) {
        return userRepository.findByCin(cin);
    }
    @Override

    public User getByCin(long cin) {
        System.out.println("CIN parameter: " + cin);
        User user = userRepository.getByCin(cin);
        System.out.println("User found: " + user);
        return user;
    }
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email "+request.email() + " already exists");
        }

        // Check if cin already exists
        Optional<User> existingCinUser = userRepository.findByCin(request.cin());
        if (existingCinUser.isPresent()) {
            throw new UserAlreadyExistsException("User with CIN " + request.cin() + " already exists");
        }

        // Check if phone number already exists

        // Check if any required information is missing
        if (StringUtils.isAnyBlank(
                request.firstName(),
                request.lastName(),
                StringUtils.defaultString(String.valueOf(request.email())),
                StringUtils.defaultString(String.valueOf(request.phone())),
                StringUtils.defaultString(String.valueOf(request.cin())),
                StringUtils.defaultString(request.password()))) {
            throw new IllegalArgumentException("Please provide all required information");
        }
        var newUser = new User();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPhone(request.phone());
        newUser.setCin(request.cin());
        Role defaultRole = roleRepository.findByName("etudiant"); // replace with your actual query
        newUser.setRoles(defaultRole);

        newUser.setPassword(passwordEncoder.encode(request.password()));
        return userRepository.save(newUser);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }
    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Verification link already expired," +
                    " Please, click the link below to receive a new verification link";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var verificationTokenTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }

    public void changePassword(User theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }

    @Override
    public User findUserByPasswordToken(String token) {
        return passwordResetTokenService.findUserByPasswordToken(token).get();
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
    }
    @Override
    public boolean oldPasswordIsValid(User user, String oldPassword){
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkCinExists(long cin) {
        return userRepository.existsByCin(cin);
    }

    @Override
    public User updateUserProfile(Long userId, User updatedUser) {
        // Vérifiez si l'utilisateur avec l'ID userId existe
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            // Mettez à jour les propriétés du profil utilisateur
            User existingUser = optionalUser.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setCin(updatedUser.getCin());

            // Mettez à jour d'autres propriétés du modèle User si nécessaire

            // Enregistrez les modifications dans la base de données
            userRepository.save(existingUser);

            return existingUser;
        } else {
            // Utilisateur non trouvé
            return null;
        }
    }
}

