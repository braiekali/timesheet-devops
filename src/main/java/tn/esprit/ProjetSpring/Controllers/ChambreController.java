package tn.esprit.ProjetSpring.Controllers;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.BlocRepository;
import tn.esprit.ProjetSpring.Services.FileStorageService;
import tn.esprit.ProjetSpring.Services.IChambreService;
import tn.esprit.ProjetSpring.entities.Bloc;
import tn.esprit.ProjetSpring.entities.Chambre;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/chambre")


public class ChambreController {
    IChambreService chambreService;
    BlocRepository blocRepository;
    FileStorageService fileStorageService;


    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/uploadImage/{id}")
    public Chambre handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return chambreService.handleImageFileUpload(fileImage,id);
    }

    @GetMapping("/getImage/{fileName:.+}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String fileName) {
        ByteArrayResource resource = fileStorageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust the media type based on your image type
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }






   /* @PostMapping("/addchambre")
    public Chambre addChambre(@RequestBody Chambre chambre) {

        return chambreService.addChambre(chambre);
    }*/

    @PostMapping("/addchambree")
    public Chambre addChambre(@RequestBody Chambre chambre) {
        Bloc bloc = blocRepository.findById(chambre.getBloc().getIdBloc()).orElseThrow(() -> new IllegalArgumentException("Club non trouvé avec l'ID : "));
        chambre.setBloc(bloc);
        // chambre.setReservation(null);
        return chambreService.addChambre(chambre);
    }

    @GetMapping("/chambre/{id}")
    public Chambre retrieveChambre(@PathVariable Long id) {

        return chambreService.getChambre(id);
    }

    @GetMapping("/chambres")

    public List<Chambre> retrieveAllChambres() {

        return chambreService.getAllChambres();
    }

    @DeleteMapping("/chambre/{id}")
    public void deleteChambre(@PathVariable long id) {

        chambreService.deleteChambre(id);
    }

    @PutMapping("/chambre")
    public Chambre updateChambre(@RequestBody Chambre chambre) {
        return chambreService.updateChambre(chambre);
    }

    @GetMapping("/foyer/{idFoyer}/{idBloc}")
    public List<Chambre> getChambresByFoyerAndBloc(
            @PathVariable long idFoyer,
            @PathVariable long idBloc) {

        return  chambreService.getChambresByFoyerAndBloc(idFoyer, idBloc);

    }
}




