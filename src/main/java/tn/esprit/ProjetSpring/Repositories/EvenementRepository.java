package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Actualite;
import tn.esprit.ProjetSpring.entities.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement,Long> {
}
