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
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idReservation;
    Date anneeUniv;
    boolean estValide;

    @OneToOne(mappedBy = "reservation")
    @JsonIgnoreProperties("reservation")
    Chambre chambre;

    @ManyToOne
    private User user;
}

