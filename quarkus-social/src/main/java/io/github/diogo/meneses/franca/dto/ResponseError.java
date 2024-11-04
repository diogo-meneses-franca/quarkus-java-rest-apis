package io.github.diogo.meneses.franca.dto;

import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

	private String message;
	private Collection<FieldError> errors;

	public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>>violations){
		Set<FieldError> errors = violations
				.stream()
				.map(violation -> new FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
				.collect(Collectors.toSet());
		String message = "Validation error:";
		return new ResponseError(message, errors);
	}

}
