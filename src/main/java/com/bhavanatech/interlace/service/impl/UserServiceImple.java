package com.bhavanatech.interlace.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bhavanatech.interlace.entities.*;
import com.bhavanatech.interlace.exceptions.ResourceNotFoundException;
import com.bhavanatech.interlace.model.UserDto;
import com.bhavanatech.interlace.repositories.UserRepository;
import com.bhavanatech.interlace.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImple implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;


	@Override
	public UserDto createUser(UserDto userDto) {
		UserEntity user = this.dtoToUser(userDto);
		UserEntity savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto , Integer userId) {
		UserEntity user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		

		UserEntity updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		UserEntity user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<UserEntity> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		UserEntity user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
		
	}
	public UserEntity dtoToUser(UserDto userDto) {
		UserEntity user = this.modelMapper.map(userDto, UserEntity.class);
		return user;
	}

	public UserDto userToDto(UserEntity user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}


}
