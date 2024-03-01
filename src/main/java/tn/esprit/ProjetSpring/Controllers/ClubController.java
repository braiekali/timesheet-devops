package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Services.IClubService;
import tn.esprit.ProjetSpring.entities.Club;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class ClubController {
    IClubService clubService;

    @PostMapping("/dashboard/clubs/addClub/{nomUni}")
    Club addClub(@RequestBody Club club,@PathVariable String nomUni){
        return clubService.addClub(club,nomUni);
    }

    @GetMapping("/dashboard/clubs/getOneClub/{id}")
    Club getClub(@PathVariable Long id){
        return clubService.getClub(id);
    }

    @GetMapping("/dashboard/clubs")
    List<Club> getAllClubs(){
        return clubService.getAllClubs();
    }

    @DeleteMapping("/dashboard/clubs/deleteClub/{id}")
    void deleteClubById(@PathVariable Long id){
        this.clubService.deleteClubById(id);
    }

    @PutMapping("/dashboard/clubs/updateClub/{nomUni}")
    Club updateClub(@RequestBody Club club,@PathVariable String nomUni){
        return  this.clubService.updateClub(club, nomUni);
    }
	//-----------------
    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/dashboard/clubs/uploadImage/{id}")
    public Club handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return clubService.handleImageFileUpload(fileImage,id);
    }
}
