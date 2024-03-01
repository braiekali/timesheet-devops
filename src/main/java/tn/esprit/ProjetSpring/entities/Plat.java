package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPlat;

    private String nomPlat;
    private Long prixPlat;
    private String description;
    private String  imagePlat;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonManagedReference
    private Restaurant restaurant;

}