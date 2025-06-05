package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.ReservationDto;
import com.softwarearchitecture.QuickBook.Exception.AlreadyExistsException;
import com.softwarearchitecture.QuickBook.Exception.ResourceNotFoundException;
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

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  HotelRepository hotelRepository,
                                  RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public ReservationDto getReservationById(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new ResourceNotFoundException("Reservation not found with id: " + reservationId);
        }
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
    public ReservationDto saveReservation(ReservationDto dto) {
        User user = userRepository.findById(dto.getU_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Hotel hotel = hotelRepository.findById(dto.getH_id())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = roomRepository.findById(dto.getR_id())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Tarih çakışma kontrolü
        List<Reservation> existingReservations = reservationRepository.findByRoom_RoomId(room.getRoomId());
        for (Reservation reservation : existingReservations) {
            if (isOverlapping(dto.getStart_date(), dto.getEnd_date(),
                    reservation.getStart_date(), reservation.getEnd_date())) {
                throw new AlreadyExistsException("Room is already reserved for the selected dates.");
            }
        }

        Reservation reservation = ReservationMapper.mapToReservation(dto, user, hotel, room);
        Reservation saved = reservationRepository.save(reservation);
        return ReservationMapper.mapToReservationDto(saved);
    }

    /**
     * İki tarih aralığının çakışıp çakışmadığını kontrol eder.
     * Çakışma durumu: Yeni rezervasyon başlangıcı mevcut rezervasyon bitişinden önce VE
     *                 Yeni rezervasyon bitişi mevcut rezervasyon başlangıcından sonra
     */
    private boolean isOverlapping(java.time.LocalDate newStart, java.time.LocalDate newEnd,
                                  java.time.LocalDate existingStart, java.time.LocalDate existingEnd) {
        // Çakışma varsa true döner
        // Çakışma yoksa false döner
        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }
}