package tn.esprit.ProjetSpring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ProjetSpring.entities.Chambre;
import tn.esprit.ProjetSpring.entities.Reservation;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre,Long> {
    Chambre findByNumeroChambre(Long num);
    Chambre findByReservationIdReservation(Long id);
    Chambre findByReservation(Reservation r);
    List<Chambre> findByBloc_Foyer_IdFoyerAndBloc_IdBloc(long idFoyer, long idBloc);

}
