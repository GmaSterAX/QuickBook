package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.ActivityDto;
import com.softwarearchitecture.QuickBook.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class ActivityContoller {

    private final ActivityService activityService;

    @Autowired
    public ActivityContoller(ActivityService activityService){
        this.activityService = activityService;
    }

    @GetMapping("/{hotelId}/activities")
    public ResponseEntity<List<ActivityDto>> getAllByHotelId(@PathVariable("hotelId") long hotelId){
        List<ActivityDto> activities = activityService.getAllByHotelId(hotelId);
        return ResponseEntity.ok(activities);
    }
}
