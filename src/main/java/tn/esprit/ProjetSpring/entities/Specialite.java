package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specialite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String nom;
    String diplome;
    String description;
    String planEtudePdf;
    String imageUrl;
    @OneToMany(mappedBy = "specialite", cascade = CascadeType.ALL)
    //@JsonIgnoreProperties("specialite")
    Set<Matiere> matieres = new HashSet<>();
    @JsonIgnoreProperties("specialites")
    @ManyToOne
    Universite universite;
}
