package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.BlocRepository;
import tn.esprit.ProjetSpring.Repositories.ChambreRepository;
import tn.esprit.ProjetSpring.entities.Bloc;
import tn.esprit.ProjetSpring.entities.Chambre;

import java.util.List;
@Service
@AllArgsConstructor
public class ChambreService implements IChambreService {


    ChambreRepository chambreRepository;
    BlocRepository blocRepository;
    FileStorageService fileStorageService;


    @Override
    public Chambre addChambree(Chambre chambre,long idBloc) {
        Bloc b=blocRepository.findById(idBloc).orElse(null);
        chambre.setBloc(b);
        return chambreRepository.save(chambre);
    }


    @Override
    public Chambre addChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public Chambre getChambre(Long id) {
        return chambreRepository.findById(id).orElse(null);
    }



    @Override
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    @Override
    public void deleteChambre(long idChambre) {
        chambreRepository.deleteById(idChambre);
    }

    @Override
    public Chambre updateChambre(Chambre chambre) {
        Chambre ch=chambreRepository.findById(chambre.getIdChambre()).orElse(null);
        if (ch!=null)
            chambreRepository.save(chambre);
        return  ch;
    }

    @Override
    public Chambre handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        Chambre chambre = chambreRepository.findById(id).orElse(null);
        chambre.setImageUrl(fileName);
        return chambreRepository.save(chambre);
    }

    //nawres
    public List<Chambre> getChambresByFoyerAndBloc(long idFoyer, long idBloc) {
        return chambreRepository.findByBloc_Foyer_IdFoyerAndBloc_IdBloc(idFoyer, idBloc);
    }
}