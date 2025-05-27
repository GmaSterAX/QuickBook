package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.ActivityDto;
import com.softwarearchitecture.QuickBook.Model.Activity;

public class ActivityMapper {

    public static ActivityDto mapToActivityDto(Activity activity){
        return new ActivityDto(
                activity.getId(),
                activity.getName(),
                activity.getHotel().getId()
        );
    }
}
