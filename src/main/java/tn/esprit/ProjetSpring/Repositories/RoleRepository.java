package tn.esprit.ProjetSpring.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.ProjetSpring.entities.Reservation;
import tn.esprit.ProjetSpring.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);


}
