package io.github.diogo.meneses.franca.utils;

import io.github.diogo.meneses.franca.dto.ResponseError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@ApplicationScoped
public class ValidatorUtil {

	private final Validator validator;

	public <T> Response validate(T request) {
		Set<ConstraintViolation<T>> violations = validator.validate(request);
		if (!violations.isEmpty()) {
			ResponseError responseError = ResponseError.createFromValidation(violations);
			return Response.status(400).entity(responseError).build();
		}
		return null;
	}
}
