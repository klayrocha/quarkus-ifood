package com.klayrocha.ifood.cadastro.infra;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ConstraintViolationImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Attribute path, eg: Start Date, person.address.number", required = false)
	private final String atributo;

	@Schema(description = "Descriptive error message possibly associated with the path", required = true)
	private final String message;

	private ConstraintViolationImpl(ConstraintViolation<?> violation) {
		this.message = violation.getMessage();
		this.atributo = Stream.of(violation.getPropertyPath().toString().split("\\.")).skip(2)
				.collect(Collectors.joining("."));
	}

	public ConstraintViolationImpl(String atributo, String message) {
		this.message = message;
		this.atributo = atributo;
	}

	public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
		return new ConstraintViolationImpl(violation);
	}

	public static ConstraintViolationImpl of(String violation) {
		return new ConstraintViolationImpl(null, violation);
	}

	public String getMessage() {
		return message;
	}

	public String getAtributo() {
		return atributo;
	}

}