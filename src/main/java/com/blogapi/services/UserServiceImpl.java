package com.blogapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Role;
import com.blogapi.entities.User;
import com.blogapi.exception.ResourseNotFoundException;
import com.blogapi.payloads.UserDto;
import com.blogapi.repositories.RoleRepo;
import com.blogapi.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
//	 private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		
		User user=this.dtotoUser(userDto);
		User save = this.userRepo.save(user);
		
		return this.usertoDto(save);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long id) {

		User user = this.userRepo.findById(id).orElseThrow(()-> new ResourseNotFoundException("User","Id",id));
		
//		System.out.print(user);
			
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());
		User save = this.userRepo.save(user);
		
		return this.usertoDto(save);
	}

	@Override
	public UserDto getUserById(Long id) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(id).orElseThrow(()-> new ResourseNotFoundException("User","Id",id));
		
       
		
		return this.usertoDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
			
		List<User> users=this.userRepo.findAll();
		List<UserDto> collect = users.stream().map(user-> this.usertoDto(user)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteUserById(Long id) {

		User user = this.userRepo.findById(id).orElseThrow(()-> new ResourseNotFoundException("User"," Id ",id));
		
		this.userRepo.delete(user);
		
	}
	
	
	public User dtotoUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto, User.class);
		
//		User user =new User();				
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		
		
		return user;
	}
	
	public UserDto usertoDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		
		
		return userDto;
	}

	
	public UserDto registerNewUser(UserDto userDto) {
		
		

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		long id=502;
		Role role = this.roleRepo.findById(id).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

}
