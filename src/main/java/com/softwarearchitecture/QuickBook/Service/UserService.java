package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(long userId);
    UserDto getUserByName(String userName);
    UserDto getUserByMail(String userMail);
    List<UserDto> getAllUsers();
    UserDto updateUser(long userId, UserDto updatedUser);
    void deleteUser(long userId);
}
