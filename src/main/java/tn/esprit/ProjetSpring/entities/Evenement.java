package tn.esprit.ProjetSpring.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEvent;
    String nomEvent;
    String descriptionEvent;
    Date dateDebEvent;
    Date dateFinEvent;
    String lieuEvent;
    String imageEvent;

    @ManyToOne
    @JoinColumn(name = "club")
    Club club;

}
