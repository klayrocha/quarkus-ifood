package com.klayrocha.ifood.pedido.dto;

import java.math.BigDecimal;

public class DishOrdeerDTO {

	public String name;

	public String description;

	public BigDecimal price;

	public DishOrdeerDTO() {
		super();
	}

	public DishOrdeerDTO(String name, String description, BigDecimal price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}

}
