package tn.esprit.ProjetSpring.Services;

import io.micrometer.common.util.internal.logging.InternalLogger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Repositories.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.ProjetSpring.entities.Restaurant;

import java.util.List;

@Service
@AllArgsConstructor

public class RestaurantService implements IRestaurantService{
    RestaurantRepository restaurantRepository;

    @Autowired
    FileStorageService fileStorageService;


    @Override
    public Restaurant addRestaurant(Restaurant restaurant, MultipartFile imageRestaurant) {
        String imageUrl = fileStorageService.storeFile(imageRestaurant);
        restaurant.setImageRestaurant(imageUrl);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant, MultipartFile imageRestaurant) {
        Restaurant re = restaurantRepository.findById(restaurant.getIdRestaurant()).orElse(null);
        if (re != null) {
            String imageUrl = fileStorageService.storeFile(imageRestaurant);

            restaurant.setImageRestaurant(imageUrl);


            // Save the updated restaurant to the repository
            restaurantRepository.save(restaurant);

        }
        return re;
    }


    @Override
    public void deleteRestaurant(long id) {
        restaurantRepository.deleteById(id);

    }

    public Restaurant handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }


        String fileName = fileStorageService.storeFile(fileImage);
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurant.setImageRestaurant(fileName);
        return restaurantRepository.save(restaurant);
    }
}