package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Mapper.HotelMapper;
import com.softwarearchitecture.QuickBook.Mapper.HotelServiceMapper;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import com.softwarearchitecture.QuickBook.Repository.HotelRepository;
import com.softwarearchitecture.QuickBook.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository){
        this.hotelRepository = hotelRepository;
    }

    @Override
    public HotelDto getHotelById(long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return HotelMapper.mapToHotelDto(hotel);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream()
                .map(HotelMapper::mapToHotelDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelDto> getHotelByCity(String city) {
        List<Hotel> hotels = hotelRepository.findAllByCityIgnoreCase(city);
        return hotels.stream()
                .map(HotelMapper::mapToHotelDto)
                .toList();
    }

    @Override
    public List<HotelDto> getHotelByPoint(double point) {
        if (point < 0 || point > 10)
            throw new IllegalArgumentException("Points must be 0-10");

        double max = point + 1;

        List<Hotel> hotels = hotelRepository.findAllByPointBetween(point, max);
        return hotels.stream()
                .map(HotelMapper::mapToHotelDto)
                .toList();
    }


    @Override
    public HotelDto getHotelByName(String hotelName) {
        Hotel hotel = hotelRepository.findByName(hotelName);
        return HotelMapper.mapToHotelDto(hotel);
    }
}
