package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Exception.ResourceNotFoundException;
import com.softwarearchitecture.QuickBook.Mapper.UserMapper;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Service.UserService;

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

        User user = UserMapper.mapToUsers(userDto);
        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id : " + userId));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(long userId, UserDto updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User is not found with given id : " + userId));

        user.setName(updatedUser.getName());
        user.setMail(updatedUser.getMail());
        user.setPhone(updatedUser.getPhone());
        user.setPassword(updatedUser.getPassword());

        User updatedUserObj = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUserObj);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User is not found with given id : " + userId));
        userRepository.deleteById((long) userId);
    }

    @Override
    public UserDto getUserByName(String userName) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given name : " + userName));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto getUserByMail(String userMail) {
        User user = userRepository.findByMail(userMail)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given name : " + userMail));
        return UserMapper.mapToUserDto(user);}


}
