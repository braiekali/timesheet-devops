package tn.esprit.ProjetSpring.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Reservation;
import tn.esprit.ProjetSpring.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}