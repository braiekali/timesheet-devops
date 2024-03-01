package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Chambre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idChambre;
    long numeroChambre;
    @Enumerated(EnumType.STRING)
    TypeChambre typeChambre;

    String imageUrl;

    @ManyToOne
    @JsonIgnoreProperties("chambre")
    Bloc bloc;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("chambre")
    private Reservation reservation;

}