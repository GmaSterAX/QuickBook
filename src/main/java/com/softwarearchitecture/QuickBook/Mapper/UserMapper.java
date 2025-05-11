package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Model.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getMail(),
                user.getPhone(),
                user.getPassword()
        );
    }

    public static User mapToUsers(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getMail(),
                userDto.getPhone(),
                userDto.getPassword()
        );
    }
}
