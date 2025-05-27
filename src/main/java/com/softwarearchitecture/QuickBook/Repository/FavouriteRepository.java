package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Favourite;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    boolean existsByUserIdAndHotelId(long userId, long hotelId);
    Optional<Favourite> findByUserIdAndHotelId(long userId, long hotelId);
    List<Favourite> findByUserId(long userId);

}


    /////List<Favourite> findByUserIdAndHotelId(Long userId, Long hotelId);

