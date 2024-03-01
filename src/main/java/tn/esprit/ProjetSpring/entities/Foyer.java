package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Foyer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idFoyer;
    String nomFoyer;
    long capaciteFoyer;
    long superficie;
    String imageFoyer;

    @JsonIgnoreProperties("foyer")
    @OneToOne(mappedBy="foyer")
    private Universite universite;
    @OneToMany(mappedBy="foyer",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("foyer")
    @JsonIgnore
//    @JsonBackReference
    private Set<Bloc> blocs;

}