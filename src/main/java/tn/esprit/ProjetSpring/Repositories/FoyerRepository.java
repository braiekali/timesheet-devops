package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Foyer;

import java.util.List;

public interface FoyerRepository extends JpaRepository<Foyer,Long> {
    Foyer findByUniversiteIdUniversite(long idUniversite);
}
