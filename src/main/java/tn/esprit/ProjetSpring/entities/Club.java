package tn.esprit.ProjetSpring.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Club implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClub;
    String nomClub;
    String descriptionClub;
    String imageClub;

    @ManyToOne
    @JoinColumn(name = "universite")
    Universite universite;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("club")
    private Set<Evenement> evenements;
}