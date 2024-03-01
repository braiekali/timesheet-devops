package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ProjetSpring.Repositories.BlocRepository;
import tn.esprit.ProjetSpring.Repositories.ChambreRepository;
import tn.esprit.ProjetSpring.Repositories.FoyerRepository;
import tn.esprit.ProjetSpring.entities.Bloc;
import tn.esprit.ProjetSpring.entities.Foyer;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BlocService implements IBlocService{

    BlocRepository blocRepository;
    ChambreRepository chambreRepository;
    FoyerRepository foyerRepository;

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc getBloc(Long id) {
        return blocRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public void deleteBloc(long idBloc) {
        blocRepository.deleteById(idBloc);

    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        Bloc bl=blocRepository.findById(bloc.getIdBloc()).orElse(null);
        if (bl!=null)
            blocRepository.save(bloc);
        return  bl;
    }

    @Override
    public List<Bloc> getBlocsByFoyerId(long idFoyer) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);

        return blocRepository.findByFoyer(foyer);
    }

    public List<Bloc> updateBlocForFoyer(long foyerId, long blocId, Bloc updatedBloc) {
        // Récupérer le foyer
        Optional<Foyer> optionalFoyer = foyerRepository.findById(foyerId);
        if (optionalFoyer.isPresent()) {
            Foyer foyer = optionalFoyer.get();

            // Récupérer le bloc à mettre à jour
            Optional<Bloc> optionalBloc = foyer.getBlocs().stream()
                    .filter(b -> b.getIdBloc() == blocId)
                    .findFirst();

            if (optionalBloc.isPresent()) {
                Bloc existingBloc = optionalBloc.get();

                // Mettre à jour les propriétés du bloc
                existingBloc.setNomBloc(updatedBloc.getNomBloc());
                existingBloc.setCapaciteBloc(updatedBloc.getCapaciteBloc());
                existingBloc.setNombreEtage(updatedBloc.getNombreEtage());
                existingBloc.setImageBloc(updatedBloc.getImageBloc());

                // Enregistrer les modifications
                blocRepository.save(existingBloc);

            }
        }
        return null;
    }


    public Bloc findBlocByIdFoyerAndIdBloc(long idFoyer, long idBloc) {
        Optional<Bloc> optionalBloc = blocRepository.findById(idBloc);

        if (optionalBloc.isPresent()) {
            Bloc bloc = optionalBloc.get();

            // Assurez-vous que le bloc appartient au foyer avec l'ID correspondant
            if (bloc.getFoyer() != null && bloc.getFoyer().getIdFoyer() == idFoyer) {
                return bloc;
            }
        }
        return null;
    }







}