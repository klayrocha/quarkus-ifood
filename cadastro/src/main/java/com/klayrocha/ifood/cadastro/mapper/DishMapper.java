package com.klayrocha.ifood.cadastro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.klayrocha.ifood.cadastro.dto.AddDishDTO;
import com.klayrocha.ifood.cadastro.dto.DishDTO;
import com.klayrocha.ifood.cadastro.dto.UpdateDishDTO;
import com.klayrocha.ifood.cadastro.model.Dish;

@Mapper(componentModel = "cdi")
public interface DishMapper {

	DishDTO toDTO(Dish d);

	Dish toDish(AddDishDTO dto);

	void toDish(UpdateDishDTO dto, @MappingTarget Dish dish);

}