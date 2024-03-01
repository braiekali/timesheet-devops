package tn.esprit.ProjetSpring.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actualite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idActualite;
    String titreActualite;
    String description;
    Date dateActualite;
    @JsonIgnoreProperties("actualites")

    @ManyToOne()
    Universite universite;



}
