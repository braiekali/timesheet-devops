package tn.esprit.ProjetSpring.entities;

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
public class Bloc implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idBloc;

   String nomBloc;
    String imageBloc;
   long capaciteBloc;
    long nombreEtage;

    @ManyToOne
    Foyer foyer;
    @OneToMany(mappedBy = "bloc",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("bloc")
    private Set<Chambre> chambres;


}
