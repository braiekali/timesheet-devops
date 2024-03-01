package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.ClubRepository;
import tn.esprit.ProjetSpring.Repositories.UniversiteRepository;
import tn.esprit.ProjetSpring.entities.Club;
import tn.esprit.ProjetSpring.entities.Universite;

import java.util.List;

@Service
@AllArgsConstructor
public class ClubService implements  IClubService{
    ClubRepository clubRepository;
    UniversiteRepository universiteRepository;
    FileStorageService fileStorageService;


    @Transactional
    @Override
    public Club addClub(Club club, String nomUni){
        try {
            Universite universite =  universiteRepository.findByNomUniversite(nomUni);
            club.setUniversite(universite);
            return clubRepository.save(club);
        } catch (Exception e) {
            e.printStackTrace(); // Log or print the exception
            throw e; // Re-throw the exception if needed
        }
    }

    @Override
    public Club getClub(Long idClub){
        return this.clubRepository.findById(idClub).orElse(null);
    }

    @Override
    public List<Club> getAllClubs(){
        return  this.clubRepository.findAll();
    }

    @Override
    public void deleteClubById(Long idCLub){
        this.clubRepository.deleteById(idCLub);
    }


    @Override
    public Club updateClub(Club club,String nomUni) {
        Club oldClub = clubRepository.findById(club.getIdClub()).orElse(null);
        Universite universite = universiteRepository.findByNomUniversite(nomUni);
        club.setUniversite(universite);
        club.setImageClub(oldClub.getImageClub());
        return clubRepository.save(club);
    }

    public Club handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        Club club = clubRepository.findById(id).orElse(null);
        club.setImageClub(fileName);
        return clubRepository.save(club);
    }
}
