package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Bloc;
import tn.esprit.ProjetSpring.entities.Foyer;

import java.util.List;

public interface BlocRepository extends JpaRepository<Bloc,Long> {
    Bloc findByNomBloc(String nom);

    List<Bloc> findByFoyer(Foyer foyer);
}
