package com.softwarearchitecture.QuickBook.Service.Impl;


import com.softwarearchitecture.QuickBook.Dto.ReservationDto;
import com.softwarearchitecture.QuickBook.Mapper.ReservationMapper;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import com.softwarearchitecture.QuickBook.Model.Reservation;
import com.softwarearchitecture.QuickBook.Model.Room;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.HotelRepository;
import com.softwarearchitecture.QuickBook.Repository.ReservationRepository;
import com.softwarearchitecture.QuickBook.Repository.RoomRepository;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository){
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public ReservationDto getReservationById(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        return ReservationMapper.mapToReservationDto(reservation);
    }

    @Override
    public List<ReservationDto> getReservationByUserId(long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream()
                .map(ReservationMapper::mapToReservationDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto createReservation(ReservationDto dto) {
        User user = userRepository.findById(dto.getU_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Hotel hotel = hotelRepository.findById(dto.getH_id())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = roomRepository.findById(dto.getR_id());

        Reservation reservation = ReservationMapper.mapToReservation(dto, user, hotel, room);
        Reservation saved = reservationRepository.save(reservation);
        return ReservationMapper.mapToReservationDto(saved);
    }


}