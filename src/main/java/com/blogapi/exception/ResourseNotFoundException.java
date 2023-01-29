package com.blogapi.exception;

import lombok.Data;

@Data
public class ResourseNotFoundException extends RuntimeException {
	
	 String resourseName;
	 String fieldName;
	 Long value;
	public ResourseNotFoundException(String resourseName, String fieldName, Long value) {
		super(String.format("%s not found with %s : %s",resourseName,fieldName,value));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.value = value;
	}
	 
	 

}
