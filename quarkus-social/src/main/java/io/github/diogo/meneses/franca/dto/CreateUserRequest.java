package io.github.diogo.meneses.franca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUserRequest {

	@NotBlank(message = "Name is required!")
	private String name;

	@NotNull(message = "Age is required!")
	private Integer age;

	public CreateUserRequest(Integer age, String name) {
		this.age = age;
		this.name = name;
	}

	public CreateUserRequest(){}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
