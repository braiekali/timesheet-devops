package tn.esprit.ProjetSpring.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.entities.Plat;

import java.util.List;

public interface IPlatService {
    Plat addPlat(Plat plat, MultipartFile imagePlat);

    Plat getPlatByid(Long id);

    List<Plat> getAllPlats();

    void deletePlat(long idPlat);

    Plat updatePlat(Plat plat, long idPlat,MultipartFile imagePlat);

    List<Plat> findByRestaurant_IdRestaurant(Long restaurantId);
}