package tn.esprit.ProjetSpring.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.ProjetSpring.Repositories.ChambreRepository;
import tn.esprit.ProjetSpring.Repositories.ReservationRepository;
import tn.esprit.ProjetSpring.entities.Chambre;
import tn.esprit.ProjetSpring.entities.Reservation;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    ReservationRepository reservationRepository;
    ChambreRepository chambreRepository;


    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation getReservation(long id) {
        Reservation r = reservationRepository.findById(id).orElse(null);
        Chambre ch = chambreRepository.findByReservationIdReservation(r.getIdReservation());
        r.setChambre(ch);
        return r;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(long idReservation) {
        Chambre ch = reservationRepository.findById(idReservation).get().getChambre();
        ch.setReservation(null);
        chambreRepository.save(ch);
        reservationRepository.deleteById(idReservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        Reservation res = reservationRepository.findById(reservation.getIdReservation()).orElse(null);
        if (res != null)
            reservationRepository.save(reservation);
        return res;
    }

    @Override
    public List<Reservation> getReservationUser(Long id) {
        return reservationRepository.findAllByUserIdUser(id);
    }


    private final JavaMailSender mailSender;

    @Override
    public void sendEmailReservation(Reservation r) throws MessagingException, UnsupportedEncodingException {

        String subject = " Reservation";
        String senderName = "User Reservation ";
        String mailContent = "<p> Bonjour , Votre reservation est valide .  </p>" +
                "<p>  Chambre numero  </p>" + r.getChambre().getNumeroChambre() + "<p>  Bloc   </p>" + r.getChambre().getBloc().getNomBloc() +
                "<p>  Foyer  </p>" + r.getChambre().getBloc().getFoyer().getNomFoyer() + " .";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("abidimeriam07@gmail.com", senderName);
        messageHelper.setTo(r.getUser().getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }


}