package tn.esprit.ProjetSpring.Controllers;

import io.micrometer.common.util.internal.logging.InternalLogger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ProjetSpring.Services.FileStorageService;
import tn.esprit.ProjetSpring.Services.IRestaurantService;
import tn.esprit.ProjetSpring.Services.RestaurantService;
import tn.esprit.ProjetSpring.entities.Restaurant;

import java.util.List;
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    IRestaurantService restaurantService;
    RestaurantService restauService;
    FileStorageService fileStorageService;

    @PostMapping("/admin/Restaurantadd")
    Restaurant addRestaurant(@RequestBody Restaurant restaurant, MultipartFile imageRestaurant) {

        return restaurantService.addRestaurant(restaurant, imageRestaurant);
    }
   /*   @PostMapping("/admin/Restaurantadd")
    Restaurant addRestaurant(@ModelAttribute Restaurant restaurant, @RequestParam(value= "file" , required = false) MultipartFile imageRestaurant) {
        if (imageRestaurant != null && !imageRestaurant.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(imageRestaurant);
            restaurant.setImageRestaurant(imageUrl);
        }
        return restaurantService.addRestaurant(restaurant, imageRestaurant);
    }
*/

    @PutMapping("/admin/Restaurantupdate")
    Restaurant updateRestaurant(@RequestBody Restaurant restaurant, @RequestParam(value = "file", required = false) MultipartFile imageRestaurant) {




        return   restaurantService.updateRestaurant(restaurant, imageRestaurant);

    }


    @GetMapping("/EduCal/Restaurant/{id}")
    Restaurant getRestaurantById(@PathVariable long id) {
        return restaurantService.getRestaurantById(id);
    }


    @GetMapping("/EduCal/Restaurants")
    List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @DeleteMapping("/admin/Restaurant/{id}")
    void deleteRestaurant(@PathVariable long id) {
        restaurantService.deleteRestaurant(id);
    }


    @CrossOrigin(allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/uploadImage/{id}")
    public Restaurant handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return restauService.handleImageFileUpload(fileImage, id);

    }



}