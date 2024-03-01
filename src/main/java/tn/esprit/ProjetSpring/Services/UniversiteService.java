package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.FoyerRepository;
import tn.esprit.ProjetSpring.Repositories.UniversiteRepository;
import tn.esprit.ProjetSpring.entities.Foyer;
import tn.esprit.ProjetSpring.entities.Universite;
import tn.esprit.ProjetSpring.entities.User;

import java.util.List;
@Service
@AllArgsConstructor
public class UniversiteService implements IUniversiteService {
    @Autowired
    FileStorageService fileStorageService;
    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;


    @Override
    public Universite addUniversite(Universite universite, MultipartFile imageFile) {
        String imageUrl = fileStorageService.storeFile(imageFile);
        universite.setImage(imageUrl);
        return universiteRepository.save(universite);
    }

    @Override
    public Universite getUniversite(Long id) {
        return universiteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Universite> getAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public void deleteUniversite(long idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite updateUniversite(Universite universite, MultipartFile imageFile) {
        Universite univ = universiteRepository.findById(universite.getIdUniversite()).orElse(null);
        if (univ != null) {
            if (imageFile != null && !imageFile.isEmpty()) {
                String image = fileStorageService.storeFile(imageFile);
                universite.setImage(image);
            } else {
                universite.setImage(univ.getImage());
            }

            Foyer f = univ.getFoyer();
            universite.setFoyer(f);
            universiteRepository.save(universite);
        }
        return univ;
    }



    @Override
    public Universite addUniversiteByFoyer(Universite universite, long idFoyer, MultipartFile imageFile){
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        String imageUrl = fileStorageService.storeFile(imageFile);
        universite.setImage(imageUrl);
        universite.setFoyer(foyer);
        return universiteRepository.save(universite);
    }

    @Override
    public Universite getUniversiteByFoyer(long idUniversite) {
        Universite universite=universiteRepository.findById(idUniversite).orElse(null);
        Foyer foyer=foyerRepository.findByUniversiteIdUniversite(universite.getIdUniversite());
        universite.setFoyer(foyer);
        return universite;
    }
    public List<Universite> rechercherParNom(String nomUniversite) {
        return universiteRepository.findByNomUniversiteContainingIgnoreCase(nomUniversite);
    }

}
