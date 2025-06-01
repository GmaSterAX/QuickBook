package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.ActivityDto;
import com.softwarearchitecture.QuickBook.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
    public String getAllByHotelId(@PathVariable("hotelId") long hotelId, Model model){
        List<ActivityDto> activities = activityService.getAllByHotelId(hotelId);
        model.addAttribute("activities", activities);
        return "hotel-details";
    }
}
