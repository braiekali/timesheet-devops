package tn.esprit.ProjetSpring.Services;

import jakarta.mail.MessagingException;
import tn.esprit.ProjetSpring.entities.Reservation;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation getReservation(long id);
    List<Reservation> getAllReservations();
    void deleteReservation(long idReservation);
    Reservation updateReservation(Reservation reservation);
    List<Reservation> getReservationUser(Long id);

    void sendEmailReservation(Reservation r) throws MessagingException, UnsupportedEncodingException ;


}