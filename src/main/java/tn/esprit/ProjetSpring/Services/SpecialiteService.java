package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.SpecialiteRepository;
import tn.esprit.ProjetSpring.Repositories.UniversiteRepository;
import tn.esprit.ProjetSpring.entities.Specialite;
import tn.esprit.ProjetSpring.entities.Universite;

import java.util.List;

@Service
@AllArgsConstructor
public class SpecialiteService implements ISpecialiteService{

    SpecialiteRepository specialiteRepository;
    UniversiteRepository universiteRepository;
    FileStorageService fileStorageService;

    @Transactional
    @Override
    public Specialite add(Specialite specialite, String nomUni) {
        try {
            Universite universite = universiteRepository.findByNomUniversite(nomUni);
            specialite.setUniversite(universite);
            return specialiteRepository.save(specialite);
        } catch (Exception e) {
            e.printStackTrace(); // Log or print the exception
            throw e; // Re-throw the exception if needed
        }
    }

    @Override
    public Specialite getById(Long id) {
        return specialiteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Specialite> getAll() {
        return specialiteRepository.findAll();
    }

    @Override
    public void delete(long id) {
        specialiteRepository.deleteById(id);
    }

    @Override
    public Specialite update(Specialite specialite,String nomUni) {
        Specialite oldSpecialite = specialiteRepository.findById(specialite.getId()).orElse(null);
        Universite universite = universiteRepository.findByNomUniversite(nomUni);
        specialite.setUniversite(universite);
        specialite.setImageUrl(oldSpecialite.getImageUrl());
        specialite.setPlanEtudePdf(oldSpecialite.getPlanEtudePdf());
        return specialiteRepository.save(specialite);
    }

    public Specialite handlePlanEtudeFileUpload(MultipartFile file, long id) {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(file);
        Specialite specialite = specialiteRepository.findById(id).orElse(null);
        specialite.setPlanEtudePdf(fileName);
        return specialiteRepository.save(specialite);
    }

    public Specialite handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        Specialite specialite = specialiteRepository.findById(id).orElse(null);
        specialite.setImageUrl(fileName);
        return specialiteRepository.save(specialite);
    }
}
