package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.FavouriteDto;
import com.softwarearchitecture.QuickBook.Repository.FavouriteRepository;
import com.softwarearchitecture.QuickBook.Service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouriteController {

    private FavouriteRepository favouriteRepository;
    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService){
        this.favouriteService = favouriteService;
    }

    //ADDING TO FAVOURITE
    @PostMapping("/add")
    public ResponseEntity<FavouriteDto> addToFavourites(@RequestBody FavouriteDto favouriteDto) {
        FavouriteDto added = favouriteService.addToFavourites(favouriteDto);
        return ResponseEntity.ok(added);
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
