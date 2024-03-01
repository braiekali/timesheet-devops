package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idRestaurant;

    String nomRestaurant;

    Date dateOuverture;

    Date dateFermeture;
    String imageRestaurant;
    @OneToOne
    private Universite universite;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    //@JsonBackReference
    @JsonIgnoreProperties("restaurant")
    private Set<Plat> plats;
}
