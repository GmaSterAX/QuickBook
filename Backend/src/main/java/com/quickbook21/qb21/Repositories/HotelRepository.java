package com.quickbook21.qb21.Repositories;

import com.quickbook21.qb21.models.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotels, Long> {

}
