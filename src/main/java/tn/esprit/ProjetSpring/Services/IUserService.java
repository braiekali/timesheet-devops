package tn.esprit.ProjetSpring.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.dto.RegistrationRequest;
import tn.esprit.ProjetSpring.entities.User;
import tn.esprit.ProjetSpring.entities.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {


    User addUser(User user, MultipartFile imageFile);

    User getUser (long idUser);

    List<User> getAllUsers();

    void deleteUser (long idUser);

    User updateUser (User user, MultipartFile imageFile);




    User registerUser(RegistrationRequest request);

    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);

    VerificationToken generateNewVerificationToken(String oldToken);
    void changePassword(User theUser, String newPassword);

    String validatePasswordResetToken(String token);

    User findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);

    boolean oldPasswordIsValid(User user, String oldPassword);
    Optional <User> findByCin(long cin);

    User getByCin (long cin);
    User loadUserByUsername(String email);
    public boolean checkEmailExists(String email);
    public boolean checkCinExists(long cin);
    User updateUserProfile(Long userId, User updatedUser);


}
