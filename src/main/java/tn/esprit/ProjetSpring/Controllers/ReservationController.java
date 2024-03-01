package tn.esprit.ProjetSpring.Controllers;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ProjetSpring.Repositories.ChambreRepository;
import tn.esprit.ProjetSpring.Repositories.UserRepository;
import tn.esprit.ProjetSpring.Services.ChambreService;
import tn.esprit.ProjetSpring.Services.IReservationService;
import tn.esprit.ProjetSpring.entities.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/reservation")

public class ReservationController {
    IReservationService reservationService;
    ChambreRepository chambreRepository;
    UserRepository userRepository;
    ChambreService chambreService;

    @PostMapping("/sendEmailReservation")
    public Reservation sendEmailR(@RequestBody Reservation reservation) {
        Reservation r = reservationService.getReservation(reservation.getIdReservation());
        try {
            reservationService.sendEmailReservation(r);
            return r;
        } catch (MessagingException | UnsupportedEncodingException e) {
            return r;
        }
    }
    @PostMapping("/addreservation")
    Reservation addReservation(@RequestBody Reservation reservation) {
        User user = userRepository.findById(reservation.getUser().getIdUser()).orElseThrow(() -> new IllegalArgumentException(": "));
        Chambre chambre = chambreRepository.findById(reservation.getChambre().getIdChambre()).orElseThrow(() -> new IllegalArgumentException(" "));
        reservation.setUser(user);
        //DATE****************
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        formatter.format(currentDate);
        reservation.setAnneeUniv(currentDate);
        //****************
        reservation.setChambre(chambre);
        reservationService.addReservation(reservation);
        chambre.setReservation(reservation);
        chambreRepository.save(chambre);
        return reservation;
    }

    @GetMapping("/reservationUser/{id}")
    List<Reservation> getReservationUser(@PathVariable long id) {
        List<Reservation> list2 = new ArrayList<Reservation>();
        List<Reservation> list = reservationService.getReservationUser(id);
        for (Reservation r : list) {
            Chambre ch = chambreRepository.findByReservation(r);
            r.setChambre(ch);
            list2.add(r);
        }
        return list2;
    }

    @GetMapping("/reservation/{id}")
    Reservation retrieveReservation(@PathVariable long id) {

        return reservationService.getReservation(id);
    }

    @GetMapping("/reservations")
    List<Reservation> retrieveReservation(Reservation reservation) {

        return reservationService.getAllReservations();
    }


    @PutMapping("/reservation")
    Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @DeleteMapping("/reservation/{id}")
    void deleteReservation(@PathVariable long id) {

        reservationService.deleteReservation(id);
    }

  /*  @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail() {
        try {
            emailService.sendEmail();
            return ResponseEntity.ok("Email sent successfully!");
        } catch (MessagingException | UnsupportedEncodingException e) {
            // Log the exception or handle it as needed
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }*/


}