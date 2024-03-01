package tn.esprit.ProjetSpring.jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ProjetSpring.Exception.UserNotFoundException;
import tn.esprit.ProjetSpring.Services.IUserService;
import tn.esprit.ProjetSpring.entities.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class JWTController {
    private final IUserService userService;

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()){
            User user = userService.loadUserByUsername(authRequest.getEmail()); // Fetch the user from the service
            return jwtService.generateToken(user);
        }
        else {
            throw new UserNotFoundException("Invalid user credentials");
        }
    }
}