package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ProjetSpring.Services.FoyerService;
import tn.esprit.ProjetSpring.Services.IFoyerService;
import tn.esprit.ProjetSpring.entities.Bloc;
import tn.esprit.ProjetSpring.entities.Foyer;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/foyer")
public class FoyerController {

    IFoyerService foyerService;
    FoyerService foyserv;

    @PostMapping("/addfoyer")
    Foyer addFoyer (@RequestBody Foyer foyer){
        return foyerService.addFoyer(foyer);
    }


//    @CrossOrigin(allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
//    @PostMapping("/uploadImage/{id}")
//    public Foyer handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
//        return foyserv.handleImageFileUpload(fileImage, id);
//
//    }

    @GetMapping("/foyer/{id}")
    Foyer retrievefoyer(@PathVariable Long id){

        return foyerService.getFoyer(id);
    }

    @GetMapping("/foyers")
    List<Foyer> retrieveFoyer(Foyer foyer){

        return foyerService.getAllFoyers();
    }


    @PutMapping("/foyer")
    Foyer updateFoyer (@RequestBody Foyer foyer)
    {
        return foyerService.updateFoyer(foyer);
    }

    @DeleteMapping("/foyer/{id}")
    void deleteFoyer(@PathVariable Long id){

        foyerService.deleteFoyer(id);
    }

    @PostMapping("/{idFoyer}/ajouterBloc")
    public ResponseEntity<Void> ajouterBlocAuFoyer(@PathVariable long idFoyer, @RequestBody Bloc bloc) {
        foyerService.ajouterBlocAuFoyer(idFoyer, bloc);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




    @PostMapping("/{foyerId}/addblock")
    public ResponseEntity<Foyer> addBlockToFoyer(@PathVariable Long foyerId, @RequestBody Long blocId) {
        try {
            Foyer updatedFoyer = foyerService.addBlockToFoyer(foyerId, blocId);
            return ResponseEntity.ok(updatedFoyer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/sommeCapaciteTousLesFoyers")
    public ResponseEntity<Long> obtenirSommeCapaciteTousLesFoyers() {
        long sommeCapacite = foyerService.calculerSommeCapaciteTousLesFoyers();
        return ResponseEntity.ok(sommeCapacite);
    }



}