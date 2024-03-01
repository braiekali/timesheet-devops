package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Actualite;

import java.util.Set;

public interface ActualiteRepository extends JpaRepository<Actualite,Long> {
    Set<Actualite> findActualiteByUniversiteIdUniversite(long idUniversite);
}
