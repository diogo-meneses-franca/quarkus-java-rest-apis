package io.github.diogo.meneses.franca.dto;

import jakarta.validation.ConstraintViolation;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseError {

	private String message;
	private Collection<FieldError> errors;

	public ResponseError() {
	}

	public ResponseError(String message, Collection<FieldError> errors) {
		this.message = message;
		this.errors = errors;
	}

	public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>>violations){
		Set<FieldError> errors = violations
				.stream()
				.map(violation -> new FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
				.collect(Collectors.toSet());
		String message = "Validation error:";
		return new ResponseError(message, errors);
	}

	public Collection<FieldError> getErrors() {
		return errors;
	}

	public void setErrors(Collection<FieldError> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
