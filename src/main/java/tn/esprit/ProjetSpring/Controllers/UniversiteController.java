package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Services.FileStorageService;
import tn.esprit.ProjetSpring.Services.IUniversiteService;
import tn.esprit.ProjetSpring.entities.Universite;

import java.util.List;
@CrossOrigin("*")

@RestController
@AllArgsConstructor
@RequestMapping("/universite")

public class UniversiteController {
    IUniversiteService universiteService;
    @Autowired
    FileStorageService  fileStorageService ;

    @PostMapping("/adduniversite")
    Universite addUniversite (@ModelAttribute Universite universite, @RequestParam(value = "file",required = false) MultipartFile imageFile){
        String imageUrl = fileStorageService.storeFile(imageFile);
        universite.setImage(imageUrl);
        return universiteService.addUniversite(universite,imageFile);
    }

    @GetMapping("/universite/{id}")
    Universite retrieveUniversite(@PathVariable Long id){

        return universiteService.getUniversite(id);
    }

    @GetMapping("/universites")
    List<Universite> retrieveUniversite(){
        return universiteService.getAllUniversites();}

    @PatchMapping("/universite")
    public Universite updateUniversite(@ModelAttribute Universite universite, @RequestParam(value = "file", required = false) MultipartFile imageFile) {
        if (imageFile != null){
            String image = fileStorageService.storeFile(imageFile);
            universite.setImage(image); }
        if (imageFile == null){
            universite.setImage(universite.getImage()); }
        return universiteService.updateUniversite(universite,imageFile);
    }

    @DeleteMapping("/universite/{id}")
    void deleteUniversite(@PathVariable long id){
        universiteService.deleteUniversite(id);
    }

    @PostMapping("/adduniversitebyfoyer/{idFoyer}")
    Universite addUniversiteWithFoyer(@ModelAttribute Universite universite, @PathVariable long idFoyer, @RequestParam(value = "file",required = false) MultipartFile imageFile) {
        if  (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(imageFile);
            universite.setImage(imageUrl);
        }

        return universiteService.addUniversiteByFoyer(universite,idFoyer,imageFile);
    }

    @GetMapping("/universitebyfoyer/{idUniversite}")
    public Universite getUniversiteByFoyer(long idUniversite){
        return universiteService.getUniversiteByFoyer(idUniversite);

    }
    @GetMapping("/searchUnivarsite/{nomUniversite}")
    public ResponseEntity<List<Universite>> rechercherParNom(@PathVariable String nomUniversite) {
        List<Universite> result = universiteService.rechercherParNom(nomUniversite);
        return ResponseEntity.ok(result);
    }

}

