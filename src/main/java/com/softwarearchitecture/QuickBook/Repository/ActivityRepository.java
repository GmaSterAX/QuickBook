package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByHotelId(long hotelId);
}
