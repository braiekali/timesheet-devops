package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Services.MatiereService;
import tn.esprit.ProjetSpring.entities.Matiere;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/matieres")
@CrossOrigin(origins = "*")
public class MatiereController {
    MatiereService matiereService;

    @GetMapping
    List<Matiere> getAll(){
        return matiereService.getAll();
    }

    @PostMapping("/{specId}")
    Matiere add(@RequestBody Matiere matiere,@PathVariable long specId){
        return  matiereService.add(matiere,specId);
    }

    @GetMapping("/{id}")
    Matiere getById(@PathVariable long id){
        return matiereService.getById(id);
    }

    @GetMapping("bySpec/{specId}")
    List<Matiere> getBySpec(@PathVariable long specId){
        return matiereService.getBySpec(specId);
    }

    @PutMapping
    Matiere update(@RequestBody Matiere matiere){
        return  matiereService.update(matiere);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id){
        matiereService.delete(id);
    }

    @PostMapping("/uploadImage/{id}")
    public Matiere handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return matiereService.handleImageFileUpload(fileImage,id);
    }

    @GetMapping("countBySpec/{specId}")
    int countBySpecCount(@PathVariable long specId){
        return matiereService.countBySpecCount(specId);
    }
}
