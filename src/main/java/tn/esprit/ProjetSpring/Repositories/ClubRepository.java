package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Club;

public interface ClubRepository extends JpaRepository<Club,Long> {
}
