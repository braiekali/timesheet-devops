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
public class Universite implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUniversite;
    String nomUniversite;
    String adresseUniversite;
    String ville;
    String descriptionUniversite;
    int telUniversite;
    String emailUinversite;
    String image;
    @JsonIgnoreProperties("universite")
    @OneToOne()
    private Foyer foyer;
    @JsonIgnoreProperties("universite")
    @OneToMany(cascade = CascadeType.ALL, mappedBy="universite")
    private Set<Club> clubs;
    @JsonIgnore
    @OneToOne( mappedBy = "universite")
    private Restaurant restaurant;
    @JsonIgnoreProperties("universite")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universite")
    private Set<Specialite> specialites;
    @JsonIgnoreProperties("universite")
    @OneToMany( mappedBy = "universite",cascade = CascadeType.ALL)
    private Set<Actualite> actualites;

}
