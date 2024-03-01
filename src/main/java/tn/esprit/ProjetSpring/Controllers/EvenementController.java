package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Services.IEvenementService;
import tn.esprit.ProjetSpring.entities.Evenement;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class EvenementController {
    IEvenementService eventService;

    @PostMapping("/dashboard/clubs/addEvent/{id}")
    Evenement addEvent(@RequestBody Evenement event,@PathVariable Long id){
        return eventService.addEvent(event,id);
    }

    @GetMapping("/dashboard/clubs/getOneEvent/{id}")
    Evenement getEvent(@PathVariable Long id){
        return eventService.getEvent(id);
    }

    @GetMapping("/events/front")
    List<Evenement> getAllEvent(){
        return eventService.getAllEvents();
    }

    @DeleteMapping("/dashboard/clubs/deleteEvent/{id}")
    void deleteEvent(@PathVariable Long id){
        this.eventService.deleteEvent(id);
    }

    @PutMapping("/dashboard/clubs/updateEvent/{id}")
    Evenement updateEvent(@RequestBody Evenement event,@PathVariable Long id){
        return  this.eventService.updateEvent(event,id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/dashboard/clubs/events/uploadImage/{id}")
    public Evenement handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return eventService.handleImageFileUpload(fileImage,id);
    }

    @PostMapping("/events/shareFb/{id}")
    public String shareFb(@PathVariable Long id){
        return eventService.shareFb(id);
    }

}
