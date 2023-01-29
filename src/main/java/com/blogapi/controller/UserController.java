package com.blogapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.payloads.ApiResponse;
import com.blogapi.payloads.UserDto;
import com.blogapi.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/user")
	public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUser = this.userService.createUser(userDto);
		
		return new ResponseEntity<UserDto>(createUser,HttpStatus.CREATED);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable Long id)
	{
		UserDto updateUser = this.userService.updateUser(userDto, id);
		
		return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable  Long id)
	{
		this.userService.deleteUserById(id);
		ApiResponse apiResponse=new ApiResponse();
		apiResponse.setMsg("data delete success");
		apiResponse.setStatus(true);
		return new ResponseEntity<ApiResponse>( apiResponse,HttpStatus.OK);
		
	}
	
//	@GetMapping("/users")
//	public ResponseEntity<List<UserDto>> getalluser(){
//		
//		return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(),HttpStatus.OK);
//		
//	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getalluser(){
		Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("data", this.userService.getAllUsers());
		return ResponseEntity.ok(map);
		
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getalluser( @PathVariable Long id){
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("success",true);
		   map.put("data", this.userService.getUserById(id));
		return  ResponseEntity.ok(map);
		
	}
	
	

}
