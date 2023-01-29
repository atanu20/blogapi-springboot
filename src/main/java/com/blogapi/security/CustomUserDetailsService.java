package com.blogapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapi.entities.User;
import com.blogapi.exception.ApiException;
import com.blogapi.exception.ResourseNotFoundException;
import com.blogapi.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	private long id=0;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user= userRepo.findByEmail(username).orElseThrow(()-> new ResourseNotFoundException("User ", "email : "+ username,id));
		
		User user=null;
		
		try {
			user= userRepo.findByEmail(username).get();
			
		}catch(Exception e)
		{
			throw new ApiException("Invalid username or password !!");
		}
		
		
		
		
		return user;
		
	}

}
