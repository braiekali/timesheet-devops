package tn.esprit.ProjetSpring.Services;


import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.entities.Chambre;

import java.util.List;

public interface IChambreService {
    Chambre addChambree(Chambre chambre,long idBloc);
    Chambre addChambre(Chambre chambre);
    Chambre getChambre(Long id);
    List<Chambre> getAllChambres();
    void deleteChambre(long idChambre);
    Chambre updateChambre(Chambre chambre);
    public Chambre handleImageFileUpload(MultipartFile fileImage, long id) ;
    List<Chambre> getChambresByFoyerAndBloc(long idFoyer, long idBloc);
}
