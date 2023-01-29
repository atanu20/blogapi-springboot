package com.blogapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.entities.User;
import com.blogapi.exception.ApiException;
import com.blogapi.payloads.ApiResponse;
import com.blogapi.payloads.JwtAuthRequest;
import com.blogapi.payloads.JwtAuthResponse;
import com.blogapi.payloads.UserDto;
import com.blogapi.repositories.UserRepo;
import com.blogapi.security.JwtTokenHelper;
import com.blogapi.services.UserService;

@RestController
@RequestMapping("/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser( @RequestBody UserDto userDto) {
		
		boolean existsByEmail = this.userRepo.existsByEmail(userDto.getEmail());
		if(existsByEmail)
		{
			
			ApiResponse api=new ApiResponse();
			api.setMsg("Email Already Present");
			api.setStatus(false);
			
			
			return new ResponseEntity<ApiResponse>(api,HttpStatus.BAD_REQUEST);
			
		}else {
			UserDto registeredUser = this.userService.registerNewUser(userDto);
			return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
		}
		
		
	}

	
}
