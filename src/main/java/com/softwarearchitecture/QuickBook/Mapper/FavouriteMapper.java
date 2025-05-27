package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.FavouriteDto;
import com.softwarearchitecture.QuickBook.Model.Favourite;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import com.softwarearchitecture.QuickBook.Model.User;

public class FavouriteMapper {

    public static FavouriteDto mapToFavouriteDto(Favourite favourite){
        return new FavouriteDto(
                favourite.getId(),
                favourite.getUser().getId(),
                favourite.getHotel().getId()
        );
    }

    public static Favourite mapToFavourite(FavouriteDto favouriteDto){
        Favourite favourite = new Favourite();
        favourite.setId(favouriteDto.getId());

        User user = new User();
        user.setId(favouriteDto.getUser_id());
        favourite.setUser(user);

        Hotel hotel = new Hotel();
        hotel.setId(favouriteDto.getHotel_id());
        favourite.setHotel(hotel);

        return favourite;
    }
    }

