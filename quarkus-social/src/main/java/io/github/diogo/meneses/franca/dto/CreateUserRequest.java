package io.github.diogo.meneses.franca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

	private Long id;

	@NotBlank(message = "Name is required!")
	private String name;

	@NotNull(message = "Age is required!")
	private Integer age;

}
