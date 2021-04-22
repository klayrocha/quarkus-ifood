package com.klayrocha.ifood.cadastro.dto;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.klayrocha.ifood.cadastro.infra.DTO;
import com.klayrocha.ifood.cadastro.infra.ValidDTO;
import com.klayrocha.ifood.cadastro.model.Restaurant;

@ValidDTO
public class AddRestaurantDTO implements DTO {

	@Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
	@NotNull
	public String identification;

	@Size(min = 3, max = 30)
	public String fantasyName;

	public LocalizationDTO localization;

	@Override
	public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();
		if (Restaurant.find("identification", identification).count() > 0) {
			constraintValidatorContext.buildConstraintViolationWithTemplate("Identification already registered")
					.addPropertyNode("identification").addConstraintViolation();
			return false;
		}
		return true;
	}

}
