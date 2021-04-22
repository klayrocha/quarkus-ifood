package com.klayrocha.ifood.cadastro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.klayrocha.ifood.cadastro.dto.AddRestaurantDTO;
import com.klayrocha.ifood.cadastro.dto.RestaurantDTO;
import com.klayrocha.ifood.cadastro.dto.UpdateRestaurantDTO;
import com.klayrocha.ifood.cadastro.model.Restaurant;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {

	@Mapping(target = "name", source = "fantasyName")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "updateDate", ignore = true)
	@Mapping(target = "localization.id", ignore = true)
	public Restaurant toRestaurant(AddRestaurantDTO dto);
	
	
	@Mapping(target = "name", source = "fantasyName")
	public void toRestaurant(UpdateRestaurantDTO dto, @MappingTarget Restaurant restaurant);
	
	
	@Mapping(target = "fantasyName", source = "name")
	@Mapping(target = "creationDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
	public RestaurantDTO toRestaurantDTO(Restaurant r);
}
