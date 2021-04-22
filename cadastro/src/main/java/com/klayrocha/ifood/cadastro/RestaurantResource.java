package com.klayrocha.ifood.cadastro;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.klayrocha.ifood.cadastro.dto.AddDishDTO;
import com.klayrocha.ifood.cadastro.dto.AddRestaurantDTO;
import com.klayrocha.ifood.cadastro.dto.DishDTO;
import com.klayrocha.ifood.cadastro.dto.RestaurantDTO;
import com.klayrocha.ifood.cadastro.dto.UpdateDishDTO;
import com.klayrocha.ifood.cadastro.dto.UpdateRestaurantDTO;
import com.klayrocha.ifood.cadastro.infra.ConstraintViolationResponse;
import com.klayrocha.ifood.cadastro.mapper.DishMapper;
import com.klayrocha.ifood.cadastro.mapper.RestaurantMapper;
import com.klayrocha.ifood.cadastro.model.Dish;
import com.klayrocha.ifood.cadastro.model.Restaurant;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurant")
@RolesAllowed("owner")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestaurantResource {

	@Inject
	RestaurantMapper restaurantMapper;

	@Inject
	DishMapper dishMapper;

	@GET
	public List<RestaurantDTO> findAll() {
		Stream<Restaurant> restaurants = Restaurant.streamAll();
		return restaurants.map(r -> restaurantMapper.toRestaurantDTO(r)).collect(Collectors.toList());
	}

	@POST
	@Transactional
	@APIResponse(responseCode = "201", description = "If restaurant is successfully registered")
	@APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
	public Response add(@Valid AddRestaurantDTO dto) {
		restaurantMapper.toRestaurant(dto).persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public void update(@PathParam("id") Long id, UpdateRestaurantDTO dto) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException();
		}
		Restaurant restaurant = restaurantOptional.get();
		restaurantMapper.toRestaurant(dto, restaurant);
		restaurant.persist();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public void delete(@PathParam("id") Long id) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
		restaurantOptional.ifPresentOrElse(Restaurant::delete, () -> {
			throw new NotFoundException();
		});
	}

	/** Dihes **/

	@GET
	@Path("{idRestaurant}/dishes")
	@Tag(name = "dish")
	public List<DishDTO> findAllDishes(@PathParam("idRestaurant") Long idRestaurant) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant not found");
		}
		Stream<Dish> dishes = Dish.stream("restaurant", restaurantOptional.get());
		return dishes.map(d -> dishMapper.toDTO(d)).collect(Collectors.toList());
	}

	@POST
	@Path("{idRestaurant}/dishes")
	@Transactional
	@Tag(name = "dish")
	public Response addDish(@PathParam("idRestaurant") Long idRestaurant, AddDishDTO dto) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant not found");
		}
		Dish dish = dishMapper.toDish(dto);
		dish.restaurant = restaurantOptional.get();
		dish.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{idRestaurant}/dishes/{id}")
	@Transactional
	@Tag(name = "dish")
	public void updateDish(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id, UpdateDishDTO dto) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant not found");
		}

		Optional<Dish> dishOptional = Dish.findByIdOptional(id);
		if (dishOptional.isEmpty()) {
			throw new NotFoundException("Dish not found");
		}

		Dish dish = dishOptional.get();
		dishMapper.toDish(dto, dish);
		dish.persist();
	}

	@DELETE
	@Path("{idRestaurant}/dishes/{id}")
	@Transactional
	@Tag(name = "dish")
	public void delete(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {
		Optional<Restaurant> restaurantOptional = Dish.findByIdOptional(idRestaurant);
		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant not found");
		}

		Optional<Dish> dishOptional = Dish.findByIdOptional(id);

		dishOptional.ifPresentOrElse(Dish::delete, () -> {
			throw new NotFoundException("Dish not found");
		});
	}
}