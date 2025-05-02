package com.quickbook21.qb21.Services.impl;

import com.quickbook21.qb21.Dto.UserDto;
import com.quickbook21.qb21.Exceptions.ResourceNotFoundException;
import com.quickbook21.qb21.Repositories.UserRepository;
import com.quickbook21.qb21.Services.UserService;
import com.quickbook21.qb21.mappers.UserMapper;
import com.quickbook21.qb21.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSeviceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserSeviceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        Users user = UserMapper.mapToUsers(userDto);
        Users savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id : " + userId));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(long userId, UserDto updatedUser) {
        Users user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User is not found with given id : " + userId));

        user.setName(updatedUser.getName());
        user.setMail(updatedUser.getMail());
        user.setPhone(updatedUser.getPhone());
        user.setPassword(updatedUser.getPassword());

        Users updatedUserObj = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUserObj);
    }

    @Override
    public void deleteUser(long userId) {
        Users user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User is not found with given id : " + userId));
        userRepository.deleteById((long) userId);
    }


}
