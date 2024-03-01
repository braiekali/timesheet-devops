package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ProjetSpring.Services.IActualiteService;
import tn.esprit.ProjetSpring.entities.Actualite;

import java.util.List;
import java.util.Set;

@CrossOrigin("*")

@RestController
@AllArgsConstructor
@RequestMapping("/actualite")
public class ActualiteController {
    IActualiteService actualiteService;


    @PostMapping("/addactualite")
    Actualite  addActualite (@RequestBody Actualite actualite){
        return actualiteService.addActualite(actualite);
    }

    @GetMapping("/actualite/{id}")
    Actualite retrieveActualite(@PathVariable Long id){
        return actualiteService.getActualite((id));
    }

    @GetMapping("/actualites")
    List<Actualite> retrieveActualites(Actualite actualite){
        return actualiteService.getAllActualites();
    }


    @PutMapping("/actualite")
    Actualite updateActualite (@RequestBody Actualite actualite)
    {
        return actualiteService.updateActualite(actualite);
    }

    @DeleteMapping("/actualite/{id}")
    void deleteActualite(@PathVariable Long id){

        actualiteService.deleteActualite(id);
    }
    @PostMapping("/actualite/{idUniversite}")
    public Actualite affecterUniversiteAActualite(@PathVariable long idUniversite,@RequestBody Actualite actualite){
        return actualiteService.affecterUniversiteAActualite(idUniversite,actualite);
    }

    @GetMapping("/actualitebyuniversite/{idUniversite}")
    public Set<Actualite> findActualiteByUniversite(@PathVariable long idUniversite) {
        return actualiteService.findActualiteByUniversiteIdUniversite(idUniversite);
    }

    @PutMapping  ("/updateactualiteuniversite/{idUniversite}/{idActualite}")
    public Actualite updateActualiteWithUniversite(@PathVariable long idUniversite, @PathVariable long idActualite) {
        return actualiteService.updateActualiteWithUniversite(idUniversite, idActualite);
    }

}
