package tn.esprit.ProjetSpring.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.entities.Evenement;

import java.util.List;

public interface IEvenementService {
    Evenement addEvent(Evenement event, Long idClub);

    Evenement getEvent(Long idEvent);

    List<Evenement> getAllEvents();

    void deleteEvent(Long idEvent);

    Evenement updateEvent(Evenement event, Long id);

    Evenement handleImageFileUpload(MultipartFile fileImage, long id);

    String shareFb(Long id);
}

