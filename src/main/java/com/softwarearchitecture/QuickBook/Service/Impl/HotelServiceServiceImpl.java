package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Model.HotelService;
import com.softwarearchitecture.QuickBook.Dto.HotelServiceDto;
import com.softwarearchitecture.QuickBook.Mapper.HotelServiceMapper;
import com.softwarearchitecture.QuickBook.Repository.HotelServiceRepository;
import com.softwarearchitecture.QuickBook.Service.HotelServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; //

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceServiceImpl implements HotelServiceService {

    private final HotelServiceRepository hotelServiceRepository;

    @Autowired
    public HotelServiceServiceImpl(HotelServiceRepository hotelServiceRepository) {
        this.hotelServiceRepository = hotelServiceRepository;
    }

    @Override
    public List<HotelServiceDto> getByServiceName(String serviceName) {
        List<HotelService> services = hotelServiceRepository.findByServiceName(serviceName);
        return services.stream()
                .map(HotelServiceMapper::mapToHotelServiceDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelServiceDto> getByHotelId(long hotelId) {
        List<HotelService> hotelService = hotelServiceRepository.findByHotel_Id(hotelId);
        return hotelService.stream()
                .map(HotelServiceMapper::mapToHotelServiceDto)
                .collect(Collectors.toList());
    }
}

