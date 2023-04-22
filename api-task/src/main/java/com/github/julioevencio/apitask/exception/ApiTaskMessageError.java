package com.github.julioevencio.apitask.exception;

import java.io.Serializable;
import java.util.List;

public class ApiTaskMessageError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private List<String> errors;

	public ApiTaskMessageError(String message, List<String> errors) {
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
