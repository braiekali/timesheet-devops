package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Specialite;

public interface SpecialiteRepository extends JpaRepository<Specialite,Long> {
}
