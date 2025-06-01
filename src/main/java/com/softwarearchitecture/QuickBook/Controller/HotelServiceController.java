package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.HotelServiceDto;
import com.softwarearchitecture.QuickBook.Service.HotelServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping
public class HotelServiceController {

    private final HotelServiceService hotelServiceService;

    @Autowired
    public HotelServiceController(HotelServiceService hotelServiceService) {
        this.hotelServiceService = hotelServiceService;
    }

    @GetMapping("/get-by-name/{service_name}")
    public ResponseEntity<List<HotelServiceDto>> getByServiceName(@PathVariable String serviceName) {
        List<HotelServiceDto> services = hotelServiceService.getByServiceName(serviceName);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<List<HotelServiceDto>> getByHotelId(@PathVariable("id") long hotelId) {
        List<HotelServiceDto> hotelServiceDtoList = hotelServiceService.getAllByHotelId(hotelId);
        return ResponseEntity.ok(hotelServiceDtoList);
    }
}







