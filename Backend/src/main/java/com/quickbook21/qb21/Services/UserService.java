package com.quickbook21.qb21.Services;

import com.quickbook21.qb21.Dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(int userId);
    List<UserDto> getAllUsers();
}
