package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.ProjetSpring.entities.Actualite;
import tn.esprit.ProjetSpring.entities.Plat;

import java.util.List;

public interface PlatRepository extends JpaRepository<Plat,Long> {
    List<Plat> findByRestaurant_IdRestaurant(long restaurantId);
    @Query("SELECT AVG(p.prixPlat) FROM Plat p")
    Double findAveragePrice();
}