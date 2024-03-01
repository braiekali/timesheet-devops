package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Reservation;
import tn.esprit.ProjetSpring.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(long idUser);

    Optional<User> findByEmail(String email);
    User getByEmail(String email);

    Optional<User> findByCin(long cin);
    User getByCin(long cin);
    Optional<User> findByPhone(long phone);
   boolean existsByEmail(String email);
   boolean existsByCin(long cin);

}
