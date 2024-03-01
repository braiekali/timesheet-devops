package tn.esprit.ProjetSpring.Services;

import tn.esprit.ProjetSpring.entities.Bloc;
import tn.esprit.ProjetSpring.entities.Foyer;

import java.util.List;

public interface IFoyerService {
    Foyer addFoyer(Foyer foyer);
    Foyer getFoyer(Long id);
    List<Foyer> getAllFoyers();
    void deleteFoyer(long idFoyer);
    Foyer updateFoyer(Foyer foyer);

    public  Foyer addBlockToFoyer(Long foyerId, Long blocId);

    void ajouterBlocAuFoyer(long idFoyer, Bloc bloc) ;

    long calculerSommeCapaciteTousLesFoyers();


}
