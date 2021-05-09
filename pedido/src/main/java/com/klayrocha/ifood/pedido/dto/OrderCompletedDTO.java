package com.klayrocha.ifood.pedido.dto;

import java.util.List;

public class OrderCompletedDTO {

	public List<DishOrdeerDTO> dishes;

	public RestaurantDTO restaurant;

	public String customer;

	@Override
	public String toString() {
		return "OrderCompletedDTO [dishes=" + dishes + ", restaurant=" + restaurant + ", customer=" + customer + "]";
	}

}
