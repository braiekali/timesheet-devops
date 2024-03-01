package tn.esprit.ProjetSpring.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Event.RegistrationCompleteEvent;
import tn.esprit.ProjetSpring.Security.UserRegistrationDetailsService;
import tn.esprit.ProjetSpring.Services.FileStorageService;
import tn.esprit.ProjetSpring.Services.IUserService;
import tn.esprit.ProjetSpring.Services.UserService;
import tn.esprit.ProjetSpring.entities.User;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j

@RequestMapping("/user")


public class UserController {
    private final ApplicationEventPublisher publisher; // Add this line

    @Autowired
    IUserService userService;

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    UserService userSer ;
    //des fonctions de control saisie

    @PostMapping("/adduser")
    public ResponseEntity<String> ajouter(@ModelAttribute User user, @RequestParam(value= "file" , required = false) MultipartFile imageFile , final HttpServletRequest request) {

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(imageFile);
            user.setImageUrl(imageUrl);
        }
            userService.addUser(user, imageFile);

            // Trigger user registration confirmation logic
            publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

            return ResponseEntity.status(HttpStatus.OK).body("Ajout réussi. Veuillez vérifier votre email pour compléter votre enregistrement.");

        }


    @GetMapping
    public List<User> getUsers(){return userService.getAllUsers();}
    @GetMapping("/user/{id}")
    User retrieveUserById (@PathVariable Long id){return userService.getUser(id);}
    @GetMapping("/users")
    List<User> retreiveUsers(){return userService.getAllUsers();}
    @DeleteMapping("/deleteuser/{id}")

    void deleteUser (@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PatchMapping ("/updateuser")
    public ResponseEntity<String> updateUser(@ModelAttribute User user, @RequestParam(value = "file", required = false) MultipartFile imageFile) {
        if (imageFile != null) {
            String imageUrl = fileStorageService.storeFile(imageFile);
            user.setImageUrl(imageUrl);
        }

        userService.updateUser(user, imageFile);
        return ResponseEntity.status(HttpStatus.OK).body("update done");
    }

    @GetMapping("/cin/{cin}")
    User getUserByCin (@PathVariable long cin){return userService.getByCin(cin);}


    @GetMapping("/checkEmailExists")
    public ResponseEntity<?> checkEmailExists(@RequestParam String email) {
        boolean emailExists = userService.checkEmailExists(email);
        return ResponseEntity.ok(emailExists);
    }

    @GetMapping("/checkCinExists")
    public ResponseEntity<?> checkCinExists(@RequestParam long cin) {
        boolean cinExists = userService.checkCinExists(cin);
        return ResponseEntity.ok(cinExists);
    }
    @PostMapping("/uploadImage/{id}")
    public User handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return userSer.handleImageFileUpload(fileImage,id);
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath();
    }


}
