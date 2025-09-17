package com.example.app.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(BlogNotFoundException.class)
	public ResponseEntity<String> blogNotFoundExceptionHandler(BlogNotFoundException ex) {
		logger.error("GLOBAL : Blog not found exception:  {}", ex.getMessage(), ex);
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> badRequestExceptionHandler(BadRequestException ex) {
		logger.error("GLOBAL : Bad Request exception : {}", ex.getMessage(), ex);
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> genericExceptionHandler(Exception ex) {
		logger.error("GLOBAL : Exception : {}", ex.getMessage(),ex);
		
		return new ResponseEntity<>("GLOBAL Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
