package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ProjetSpring.Repositories.ActualiteRepository;
import tn.esprit.ProjetSpring.Repositories.UniversiteRepository;
import tn.esprit.ProjetSpring.entities.Actualite;
import tn.esprit.ProjetSpring.entities.Bloc;
import tn.esprit.ProjetSpring.entities.Universite;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

public class ActualiteService implements IActualiteService {
    ActualiteRepository actualiteRepository;
    UniversiteRepository universiteRepository;
    @Override
    public Actualite addActualite(Actualite actualite) {
        return actualiteRepository.save(actualite);
    }

    @Override
    public Actualite getActualite(Long id) {
        return actualiteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Actualite> getAllActualites() {
        return actualiteRepository.findAll();
    }

    @Override
    public void deleteActualite(long idActualite) {
        actualiteRepository.deleteById(idActualite);

    }

    @Override
    public Actualite updateActualite(Actualite actualite) {
        Actualite ac=actualiteRepository.findById(actualite.getIdActualite()).orElse(null);
        if (ac!=null)
            actualiteRepository.save(actualite);
        return  ac;
    }

    @Override
    public Actualite affecterUniversiteAActualite(long idUniversite, Actualite actualite) {
        Universite universite=universiteRepository.findById(idUniversite).orElse(null);
        actualite.setUniversite(universite);
        return actualiteRepository.save(actualite);
    }

    @Override
    public Set<Actualite> findActualiteByUniversiteIdUniversite(long idUniversite) {
        return actualiteRepository.findActualiteByUniversiteIdUniversite(idUniversite);
    }

    @Override
    public Actualite updateActualiteWithUniversite(long idUniversite, long idActualite) {
        Universite universite=universiteRepository.findById(idUniversite).orElse(null);
        Actualite actualite=actualiteRepository.findById(idActualite).orElse(null);
        actualite.setUniversite(universite);
        return actualiteRepository.save(actualite);
    }

}
