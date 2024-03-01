package tn.esprit.ProjetSpring.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.entities.Club;

import java.util.List;

public interface IClubService {
    Club addClub(Club club,String nomUni);

    Club getClub(Long idClub);

    List<Club> getAllClubs();

    void deleteClubById(Long idCLub);

    Club updateClub(Club club,String nomUni);

    Club handleImageFileUpload(MultipartFile fileImage, long id);
}
