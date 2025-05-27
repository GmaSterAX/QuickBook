package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.ActivityDto;
import com.softwarearchitecture.QuickBook.Mapper.ActivityMapper;
import com.softwarearchitecture.QuickBook.Model.Activity;
import com.softwarearchitecture.QuickBook.Repository.ActivityRepository;
import com.softwarearchitecture.QuickBook.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    @Override
    public List<ActivityDto> getAllByHotelId(long hotelId) {
        List<Activity> activities = activityRepository.findAllByHotelId(hotelId);
        return activities.stream()
                .map(ActivityMapper::mapToActivityDto).
                collect(Collectors.toList());
    }
}
