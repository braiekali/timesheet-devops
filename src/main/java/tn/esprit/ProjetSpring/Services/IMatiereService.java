package tn.esprit.ProjetSpring.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.entities.Matiere;

import java.util.List;

public interface IMatiereService {
    Matiere add(Matiere matiere,long specId);
    Matiere getById(Long id);
    List<Matiere> getAll();
    void delete(long id);
    Matiere update(Matiere matiere);
    public List<Matiere> getBySpec(long specId);
    public Matiere handleImageFileUpload(MultipartFile fileImage, long id);
    public int countBySpecCount(long specId);
}
