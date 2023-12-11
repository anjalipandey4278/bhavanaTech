package com.bhavanatech.interlace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bhavanatech.interlace.model.UserDto;

@Service
public interface UserService {

	UserDto createUser(UserDto user);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);

	UserDto updateUser(UserDto user, Integer userId);

}
