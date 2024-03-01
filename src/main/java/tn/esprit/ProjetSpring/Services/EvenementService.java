package tn.esprit.ProjetSpring.Services;



import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.ClubRepository;
import tn.esprit.ProjetSpring.Repositories.EvenementRepository;
import tn.esprit.ProjetSpring.entities.Club;
import tn.esprit.ProjetSpring.entities.Evenement;

import java.util.List;

@Service
@AllArgsConstructor
public class EvenementService implements IEvenementService{
    EvenementRepository evenementRepository;
    ClubRepository clubRepository;
    FileStorageService fileStorageService;

    @Transactional
    @Override
    public Evenement addEvent(Evenement event, Long idClub){
        try {
            Club club =  clubRepository.findById(idClub).orElse(null);
            event.setClub(club);
            return evenementRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace(); // Log or print the exception
            throw e; // Re-throw the exception if needed
        }
    }

    @Override
    public Evenement getEvent(Long idEvent){
        return this.evenementRepository.findById(idEvent).orElse(null);
    }

    @Override
    public List<Evenement> getAllEvents(){
        return this.evenementRepository.findAll();
    }

    @Override
    public void deleteEvent(Long idEvent){
        this.evenementRepository.deleteById(idEvent);
    }

    @Override
    public Evenement updateEvent(Evenement event,  Long id){
        Evenement oldEvent = evenementRepository.findById(event.getIdEvent()).orElse(null);
        Club club = clubRepository.findById(id).orElse(null);
        event.setClub(club);
        event.setImageEvent(oldEvent.getImageEvent());
        return evenementRepository.save(event);
    }

    public Evenement handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        Evenement event = evenementRepository.findById(id).orElse(null);
        event.setImageEvent(fileName);
        return evenementRepository.save(event);
    }

    @Override
    public String shareFb(Long id){
        String appId = "232528662540085";
        String appSecret = "60988e9928012f06c205e07717bb4196";
        String accessTokenString = "EAADTe8xUrzUBOyvsOJhda6ZBqGzNMsmUr5hvZAnptobgN8quRQYz2SMHh4wN0E6ywfRFCgM9WUHEzZBy2howgvWMZB3MIyYyhuSgrCtBt5WG4ZC4mZBg0ZCZCWJ0qqpBG6s5KcKfqoA8SLZCi0B4VRpjLWwbdL195RwCVXelhlk0uuyAhuO2I8dTlBhXXIvmdojZA5wpZAX4BJr49TCrqwVA5aBbV8ZD";

        // Set up Facebook4J
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        // Post a status message
        Evenement event = evenementRepository.findById(id).orElse(null);

        String message = "New Event is coming !!" + "\n"+ event.getNomEvent() + "\n" + event.getLieuEvent()+ "\n" +event.getDateDebEvent()+ "\n" +event.getDateFinEvent();
        try {
            facebook.postStatusMessage(message);
            return "Status message posted successfully.";
        } catch (FacebookException e) {
            e.printStackTrace();
            System.err.println("Error posting status message: " + e.getMessage());
            return  "Erreur";
        }
    }
}
