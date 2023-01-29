package com.blogapi.payloads;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	
	private Long id;
	@NotEmpty(message="UserName length should be atleast 4")
	@Size(min=4,message="UserName length should be atleast 4")
	private String name;
	
	@NotEmpty(message="Invalid email")
	@Email(message="Invalid email")
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotEmpty
	@Size(min=6,message="Invalid Password , min 6")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

	
}
