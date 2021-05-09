package com.klayrocha.ifood.pedido.model;

import org.bson.types.Decimal128;

public class Dish {

	public String name;

	public String description;

	public Decimal128 price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Decimal128 getPrice() {
		return price;
	}

	public void setPrice(Decimal128 price) {
		this.price = price;
	}
	
	

}
