package com.klayrocha.ifood.cadastro;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
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

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.klayrocha.ifood.cadastro.dto.AddRestaurantDTO;
import com.klayrocha.ifood.cadastro.mapper.RestaurantMapper;
import com.klayrocha.ifood.cadastro.model.Dish;
import com.klayrocha.ifood.cadastro.model.Restaurant;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurant")
public class RestaurantResource {
	
	@Inject
	RestaurantMapper restaurantMapper;

    @GET
    public List<Restaurant> findAll() {
        return Restaurant.listAll();
    }
    
    @POST
    @Transactional
    public Response add(AddRestaurantDTO dto) {
    	restaurantMapper.toRestaurant(dto).persist();
    	return Response.status(Status.CREATED).build();
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, Restaurant dto) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
    	if(restaurantOptional.isEmpty()) {
    		throw new NotFoundException();
    	}
    	Restaurant restaurant =  restaurantOptional.get();
    	restaurant.name = dto.name;
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
    public List<Dish> findAllDishes(@PathParam("idRestaurant") Long idRestaurant) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    	if(restaurantOptional.isEmpty()) {
    		throw new NotFoundException("Restaurant not found");
    	}
    	return Dish.list("restaurant",restaurantOptional.get());
    }
    
    @POST
    @Path("{idRestaurant}/dishes")
    @Transactional
    @Tag(name = "dish")
    public Response addDish(@PathParam("idRestaurant") Long idRestaurant, Dish dto) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    	if(restaurantOptional.isEmpty()) {
    		throw new NotFoundException("Restaurant not found");
    	}
    	// detached to persist
    	Dish dish = new Dish();
    	dish.name = dto.name;
    	dish.description = dto.description;
    	dish.price = dto.price;
    	dish.restaurant = restaurantOptional.get();
    	dish.persist();
    	return Response.status(Status.CREATED).build();
    }
    
    @PUT
    @Path("{idRestaurant}/dishes/{id}")
    @Transactional
    @Tag(name = "dish")
    public void updateDish(@PathParam("idRestaurant") Long idRestaurant,@PathParam("id") Long id, Dish dto) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    	if(restaurantOptional.isEmpty()) {
    		throw new NotFoundException("Restaurant not found");
    	}
    	
    	Optional<Dish> dishOptional = Dish.findByIdOptional(id);
    	if(dishOptional.isEmpty()) {
    		throw new NotFoundException("Dish not found");
    	}
    	
    	Dish dish = dishOptional.get();
    	dish.price = dto.price;
    	dish.persist();
    }
    
    @DELETE
    @Path("{idRestaurant}/dishes/{id}")
    @Transactional
    @Tag(name = "dish")
    public void delete(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {
    	Optional<Restaurant> restaurantOptional = Dish.findByIdOptional(idRestaurant);
    	if(restaurantOptional.isEmpty()) {
    		throw new NotFoundException("Restaurant not found");
    	}
    	
    	Optional<Dish> dishOptional = Dish.findByIdOptional(id);
    	
    	dishOptional.ifPresentOrElse(Dish::delete, () -> {
    		throw new NotFoundException("Dish not found");
    	});
    }
}