package io.github.diogo.meneses.franca.dto;

public class FieldError {

	private String field;
	private String messsage;

	public FieldError(String field, String messsage) {
		this.field = field;
		this.messsage = messsage;
	}

	public FieldError() {
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}
}
