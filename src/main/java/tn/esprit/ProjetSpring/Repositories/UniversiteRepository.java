package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.ProjetSpring.entities.Universite;

import java.util.List;

public interface UniversiteRepository extends JpaRepository<Universite,Long> {
    List<Universite> findByNomUniversiteContainingIgnoreCase(String nomUniversite);

    Universite findByNomUniversite(String nom);
}
