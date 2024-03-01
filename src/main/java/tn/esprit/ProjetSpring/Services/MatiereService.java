package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.MatiereRepository;
import tn.esprit.ProjetSpring.Repositories.SpecialiteRepository;
import tn.esprit.ProjetSpring.entities.Matiere;
import tn.esprit.ProjetSpring.entities.Specialite;

import java.util.List;

@Service
@AllArgsConstructor
public class MatiereService implements IMatiereService{

    MatiereRepository matiereRepository;
    SpecialiteRepository specialiteRepository;
    FileStorageService fileStorageService;
    @Override
    public Matiere add(Matiere matiere,long specId) {
        Specialite specialite = specialiteRepository.findById(specId).orElse(null);
        matiere.setSpecialite(specialite);

        return matiereRepository.save(matiere);
    }

    @Override
    public Matiere getById(Long id) {
        return matiereRepository.findById(id).orElse(null);
    }

    @Override
    public List<Matiere> getAll() {
        return matiereRepository.findAll();
    }

    @Override
    public void delete(long id) {
        matiereRepository.deleteById(id);
    }

    @Override
    public Matiere update(Matiere matiere) {
        Matiere oldMatiere = matiereRepository.findById(matiere.getId()).orElse(null);
        matiere.setImageUrl(oldMatiere.getImageUrl());
        matiere.setSpecialite(oldMatiere.getSpecialite());
        return matiereRepository.save(matiere);
    }

/*    public Specialite affecterMatiereToSpecialite(long specId,long matId) {
        Specialite specialite = specialiteRepository.findById(specId).orElse(null);
        Matiere matiere = matiereRepository.findById(matId).orElse(null);

        specialite.getMatieres().add(matiere);
        return specialiteRepository.save(specialite);

    }*/
    @Override
    public List<Matiere> getBySpec(long specId) {
        Specialite specialite = specialiteRepository.findById(specId).orElse(null);

        return matiereRepository.findBySpecialite(specialite);
    }
    @Override
    public Matiere handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        Matiere matiere = matiereRepository.findById(id).orElse(null);
        matiere.setImageUrl(fileName);
        return matiereRepository.save(matiere);
    }

    @Override
    public int countBySpecCount(long specId) {
        Specialite specialite = specialiteRepository.findById(specId).orElse(null);

        return matiereRepository.countBySpecialite(specialite);
    }

}
