package tn.esprit.ProjetSpring.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.util.Date;


public record RegistrationRequest (
        @Id
        // Ajoutez l'annotation Id appropriée ici
        String firstName,
        String lastName,
        String password,
        long phone,
        @Column(unique = true)
        String email,
        @Column(unique = true)
        long cin,
        Boolean isEnabled,
        Date dateNaissance
) {
        // Ajoutez les annotations JPA nécessaires ici (par exemple, @OneToMany, @ManyToOne, etc.)
}

