package tn.esprit.ProjetSpring.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.entities.Restaurant;

import java.util.List;

public interface IRestaurantService {
    Restaurant addRestaurant(Restaurant restaurant, MultipartFile imageRestaurant);
    Restaurant getRestaurantById(long id);
    List<Restaurant> getAllRestaurants();
    Restaurant updateRestaurant(Restaurant restaurant, MultipartFile imageRestaurant);
    void deleteRestaurant (long id);
}