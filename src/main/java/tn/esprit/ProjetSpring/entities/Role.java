package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idRole;
    String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="roles")
    @JsonIgnore
    private Set<User> users;


}