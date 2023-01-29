package com.blogapi.services;

import java.util.List;

import com.blogapi.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Long id);	
	UserDto getUserById(Long id);
	List<UserDto> getAllUsers();
	void deleteUserById(Long id);
	UserDto registerNewUser(UserDto user);

}
