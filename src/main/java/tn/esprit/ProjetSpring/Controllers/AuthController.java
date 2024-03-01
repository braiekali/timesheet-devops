package tn.esprit.ProjetSpring.Controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Event.Listener.RegistrationCompleteEventListener;
import tn.esprit.ProjetSpring.Event.RegistrationCompleteEvent;
import tn.esprit.ProjetSpring.Repositories.PasswordResetTokenRepository;
import tn.esprit.ProjetSpring.Repositories.RoleRepository;
import tn.esprit.ProjetSpring.Repositories.UserRepository;
import tn.esprit.ProjetSpring.Repositories.VerificationTokenRepository;
import tn.esprit.ProjetSpring.Security.UserRegistrationDetails;
import tn.esprit.ProjetSpring.Security.UserRegistrationDetailsService;
import tn.esprit.ProjetSpring.Services.FileStorageService;
import tn.esprit.ProjetSpring.Services.PasswordResetTokenService;
import tn.esprit.ProjetSpring.Services.UserService;
import tn.esprit.ProjetSpring.dto.*;
import tn.esprit.ProjetSpring.entities.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

import tn.esprit.ProjetSpring.entities.VerificationToken;
import tn.esprit.ProjetSpring.jwt.JWTService;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/register")

public class AuthController {



    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest servletRequest;
    PasswordResetTokenRepository resetPasswordRepository;
    PasswordResetTokenService passwordResetTokenService;
    UserRegistrationDetailsService userRegistrationDetailsService ;
    JWTService jwtService ;
    @Autowired

    FileStorageService fileStorageService ;
    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success!  Please, check your email for to complete your registration";
    }


    private boolean isValidEmail(String email) {
        // Ajoutez ici la logique de validation de l'e-mail en fonction de vos besoins
        // Vous pouvez utiliser une expression régulière ou toute autre méthode de validation
        return email.matches("^.+@.+\\..+$");
    }

    private boolean hasEightDigits(Long number) {
        // Ajoutez ici la logique de validation du nombre à huit chiffres
        return (number >= 10000000L && number <= 99999999L);
    }
    @GetMapping("/verifyEmail")
    public String sendVerificationToken(@RequestParam("token") String token){

        String url = applicationUrl(servletRequest)+"/register/resend-verification-token?token="+token;

        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification link, <a href=\"" +url+"\"> Get a new verification link. </a>";
    }
    @GetMapping("/resend-verification-token")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendRegistrationVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return "A new verification link has been sent to your email," +
                " please, check to activate your account";
    }
    private void resendRegistrationVerificationTokenEmail(User theUser, String applicationUrl,
                                                          VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/register/verifyEmail?token="+verificationToken.getToken();
        eventListener.sendVerificationEmail(url);
        log.info("Click the link to verify your registration :  {}", url);
    }

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<User> user = userService.findByEmail(passwordRequestUtil.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), "*", passwordResetToken);
        }
        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/register/reset-password?token=" + passwordToken;
        eventListener.sendPasswordResetVerificationEmail(user, url); // Pass the user as a parameter
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                @RequestParam("token") String token){
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<User> theUser = Optional.ofNullable(userService.findUserByPasswordToken(token));
        if (theUser.isPresent()) {
            userService.changePassword(theUser.get(), passwordRequestUtil.getNewPassword());
            return "Password has been reset successfully";
        }
        return "Invalid password reset token";
    }


    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordRequestUtil requestUtil){
        Optional<User> optionalUser = userService.findByEmail(requestUtil.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!userService.oldPasswordIsValid(user, requestUtil.getOldPassword())){
                return "Incorrect old password";
            }

            userService.changePassword(user, requestUtil.getNewPassword());
            return "Password changed successfully";
        } else {
            return "User not found"; // or any other appropriate message
        }
    }


    @GetMapping("/session")
    public ResponseEntity<User> getProfile(HttpServletRequest request) {
        // Récupérer le token du header de la requête
        String token = request.getHeader("Authorization");

        // Extraire le nom d'utilisateur du token
        String user = jwtService.extractUsernameFromToken(token);

        // Récupérer les informations du profil de l'utilisateur
        User userProfile = userService.loadUserByUsername(user);

        // Retourner le profil de l'utilisateur en réponse
        return ResponseEntity.ok(userProfile);
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath();
    }
}


