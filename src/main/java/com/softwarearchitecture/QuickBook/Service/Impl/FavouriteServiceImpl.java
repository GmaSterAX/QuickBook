package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.FavouriteDto;
import com.softwarearchitecture.QuickBook.Exception.AlreadyExistsException;
import com.softwarearchitecture.QuickBook.Exception.ResourceNotFoundException;
import com.softwarearchitecture.QuickBook.Mapper.FavouriteMapper;
import com.softwarearchitecture.QuickBook.Model.Favourite;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.FavouriteRepository;
import com.softwarearchitecture.QuickBook.Repository.HotelRepository;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository,
                                UserRepository userRepository,
                                HotelRepository hotelRepository) {
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public FavouriteDto addToFavourites(FavouriteDto favouriteDto) {
        if (favouriteRepository.existsByUserIdAndHotelId(
                favouriteDto.getUser_id(),
                favouriteDto.getHotel_id())) {
            throw new AlreadyExistsException("Bu otel zaten favorilerinizde.");
        }

        User user = userRepository.findById(favouriteDto.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Hotel hotel = hotelRepository.findById(favouriteDto.getHotel_id())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        Favourite favourite = FavouriteMapper.mapToFavourite(favouriteDto);
        Favourite addedFavourite = favouriteRepository.save(favourite);
        return FavouriteMapper.mapToFavouriteDto(addedFavourite);
    }

    @Override
    public void removeFromFavourites(long userId, long hotelId) {
        Favourite favourite = favouriteRepository.findByUserIdAndHotelId(userId, hotelId)
                .orElseThrow(() -> new RuntimeException("Favourite not found."));
        favouriteRepository.delete(favourite);
    }

    @Override
    public List<FavouriteDto> getFavouritesByUserId(long userId) {
        List<Favourite> favourites = favouriteRepository.findByUserId(userId);
        return favourites.stream()
                .map(FavouriteMapper::mapToFavouriteDto)
                .collect(Collectors.toList());
    }
}
