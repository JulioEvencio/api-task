package com.github.julioevencio.apitask.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiMessageError> handlerException(Exception e) {
		ApiMessageError errors = new ApiMessageError("Bad Request", Arrays.asList("Bad request..."));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiMessageError> handlerRuntimeException(Exception e) {
		ApiMessageError errors = new ApiMessageError("Bad Request", Arrays.asList("Bad request..."));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiMessageError> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());

		ApiMessageError error = new ApiMessageError("Invalid data", errors);

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
	}

}