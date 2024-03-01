package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUser;

    String firstName;
    String lastName;
    String password;
    long phone;

    @Column(unique = true)
    String email;
    @Column(unique = true)
    long cin;
    String imageUrl ;


    boolean active;
    private boolean isEnabled = false;

    @ManyToOne
    Role roles;



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

}
