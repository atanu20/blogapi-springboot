package com.blogapi.exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapi.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalException {
	
	
	@ExceptionHandler(ResourseNotFoundException.class)
	public ResponseEntity<ApiResponse> resourseNotFound(ResourseNotFoundException ex){
		
		ApiResponse api=new ApiResponse();
		api.setMsg(ex.getMessage());
		api.setStatus(false);
		
		
		return new ResponseEntity<ApiResponse>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handelMethodArgsEx(MethodArgumentNotValidException ex){
		
		Map<String ,String> map=new HashMap<>();
		
		BindingResult bindingResult = ex.getBindingResult();
		bindingResult.getAllErrors().forEach((e)->{
			String field = ((FieldError)e).getField();
			String msg=e.getDefaultMessage();
			
			map.put(field,msg);
		});
		
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse> httpRequestNotSupportedException(HttpRequestMethodNotSupportedException ex)
	{
		ApiResponse api=new ApiResponse();
		api.setMsg(ex.getMessage());
		api.setStatus(false);
		
		
		return new ResponseEntity<ApiResponse>(api,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<ApiResponse> invalidDataAccessException(InvalidDataAccessApiUsageException ex){
		ApiResponse api=new ApiResponse();
		api.setMsg("Invalid Request");
		api.setStatus(false);
		
		
		return new ResponseEntity<ApiResponse>(api,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse api=new ApiResponse();
		api.setMsg(message);
		api.setStatus(false);
		
		return new ResponseEntity<ApiResponse>(api, HttpStatus.BAD_REQUEST);
	}
	
}
