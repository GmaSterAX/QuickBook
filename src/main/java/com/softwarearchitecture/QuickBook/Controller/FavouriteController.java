package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.FavouriteDto;
import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Service.FavouriteService;
import com.softwarearchitecture.QuickBook.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final UserService userService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService, UserService userService){
        this.favouriteService = favouriteService;
        this.userService = userService;
    }

    //ADDING TO FAVOURITE
    @PostMapping("add/{hotelId}")
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
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromFavourites(@RequestBody FavouriteDto favouriteDto) {
        favouriteService.removeFromFavourites(favouriteDto.getUser_id(), favouriteDto.getHotel_id());
        return ResponseEntity.ok("Favourite removed successfully.");
    }

    //ALL THE USER'S FAVOURITES
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavouriteDto>> getFavouriteByUser(@PathVariable long userId){
        List<FavouriteDto> favourites = favouriteService.getFavouritesByUserId(userId);
        return ResponseEntity.ok(favourites);
    }




}
