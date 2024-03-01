package tn.esprit.ProjetSpring.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.entities.Universite;

import java.util.List;

public interface IUniversiteService {
    Universite addUniversite(Universite universite , MultipartFile imageFile);
    Universite getUniversite(Long id);
    List<Universite> getAllUniversites();
    void deleteUniversite(long idUniversite);
    Universite updateUniversite(Universite universite,MultipartFile imageFile);
    //Universite addUniversiteWithFoyer(Universite universite,long idFoyer);
    Universite addUniversiteByFoyer(Universite universite,long idFoyer, MultipartFile imageFile);
    Universite getUniversiteByFoyer(long idUniversite);
    public List<Universite> rechercherParNom(String nomUniversite);
}
