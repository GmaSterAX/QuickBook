package com.quickbook21.qb21.mappers;

import com.quickbook21.qb21.Dto.UserDto;
import com.quickbook21.qb21.models.Users;

public class UserMapper {
    public static UserDto mapToUserDto(Users user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getMail(),
                user.getPhone(),
                user.getPassword()
        );
    }

    public static Users mapToUsers(UserDto userDto){
        return new Users(
                userDto.getId(),
                userDto.getName(),
                userDto.getMail(),
                userDto.getPhone(),
                userDto.getPassword()
        );
    }
}
