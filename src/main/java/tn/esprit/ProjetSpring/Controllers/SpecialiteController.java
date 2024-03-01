package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Services.FileStorageService;
import tn.esprit.ProjetSpring.Services.SpecialiteService;
import tn.esprit.ProjetSpring.entities.Matiere;
import tn.esprit.ProjetSpring.entities.Specialite;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/specialites")
@CrossOrigin(origins = "*")
public class SpecialiteController {
    SpecialiteService specialiteService;
    FileStorageService fileStorageService;

    @GetMapping
    List<Specialite> getAll(){
        return specialiteService.getAll();
    }

    @PostMapping("/{nomUni}")
    Specialite add(@RequestBody Specialite specialite,@PathVariable String nomUni){
        return  specialiteService.add(specialite,nomUni);
    }

    @GetMapping("/{id}")
    Specialite getById(@PathVariable long id){
        return specialiteService.getById(id);
    }

    @PutMapping("/{nomUni}")
    Specialite update(@RequestBody Specialite specialite,@PathVariable String nomUni){
        return  specialiteService.update(specialite,nomUni);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id){
        specialiteService.delete(id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/uploadPdf/{id}")
    public Specialite handlePlanEtudeFileUpload(@RequestParam("file") MultipartFile file,@PathVariable long id) {
        return specialiteService.handlePlanEtudeFileUpload(file,id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/uploadImage/{id}")
    public Specialite handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage,@PathVariable long id) {
        return specialiteService.handleImageFileUpload(fileImage,id);
    }
}
