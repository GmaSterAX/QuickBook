package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.FavouriteDto;
import com.softwarearchitecture.QuickBook.Model.Favourite;
import com.softwarearchitecture.QuickBook.Model.User;

import java.util.List;

public interface FavouriteService {

    FavouriteDto addToFavourites(FavouriteDto favouriteDto);

    // Kullanıcının favorilerinden bir oteli kaldırması
    void removeFromFavourites(long userId, long hotelId);

    // Kullanıcının tüm favori otellerini listeleme
    List<FavouriteDto> getFavouritesByUserId(long userId);
}
