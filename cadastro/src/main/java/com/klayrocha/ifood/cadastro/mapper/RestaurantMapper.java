package com.klayrocha.ifood.cadastro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.klayrocha.ifood.cadastro.dto.AddRestaurantDTO;
import com.klayrocha.ifood.cadastro.model.Restaurant;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {

	@Mapping(target = "name" , source = "fantasyName")
	public Restaurant toRestaurant(AddRestaurantDTO dto);
}
