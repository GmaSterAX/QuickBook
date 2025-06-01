package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.FavouriteDto;
import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Service.FavouriteService;
import com.softwarearchitecture.QuickBook.Service.UserService;
import com.softwarearchitecture.QuickBook.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final UserService userService;
    private final HotelService hotelService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService, UserService userService, HotelService hotelService){
        this.favouriteService = favouriteService;
        this.userService = userService;
        this.hotelService = hotelService;
    }

    //ADDING TO FAVOURITE
    @PostMapping("/favorites/add/{hotelId}")
    public ResponseEntity<String> addToFavourites(@PathVariable("hotelId") Long hotelId) {
        try {
            System.out.println("Favori ekleme isteği - Hotel ID: " + hotelId);
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            UserDto user = userService.getUserByMail(email);
            
            FavouriteDto favouriteHotel = FavouriteDto.builder()
                    .hotel_id(hotelId)
                    .user_id(user.getId())
                    .build();
             
            FavouriteDto added = favouriteService.addToFavourites(favouriteHotel);
            
            return ResponseEntity.ok("Otel favorilere eklendi");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Hata: " + e.getMessage());
        }
    }


    //REMOVING FROM FAVOURITE
    @DeleteMapping("/removefromfavourite/{hotelId}")
    public ResponseEntity<String> removeFromFavourites(@PathVariable("hotelId") Long hotelId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        Long userId = userService.getUserByMail(userMail).getId();
        favouriteService.removeFromFavourites(userId, hotelId);
        return ResponseEntity.ok("Favourite removed successfully.");
    }

    //GET USER FAVOURITES
    @GetMapping("/my-favorites")
    public String getUserFavorites(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        Long userId = userService.getUserByMail(userMail).getId();
        List<FavouriteDto> favourites = favouriteService.getFavouritesByUserId(userId);
        List<HotelDto> favoriteHotels = new ArrayList<>();
        for (FavouriteDto favourite : favourites) {
            favoriteHotels.add(hotelService.getHotelById(favourite.getHotel_id()));
        }
        model.addAttribute("hotels", favoriteHotels);
        return "my-favorites";
    }
}