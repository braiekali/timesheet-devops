package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Matiere;
import tn.esprit.ProjetSpring.entities.Specialite;

import java.util.List;

public interface MatiereRepository extends JpaRepository<Matiere,Long> {

    List<Matiere> findBySpecialite(Specialite specialite);

    int countBySpecialite(Specialite specialite);
}
