package com.klayrocha.ifood.mp;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.klayrocha.ifood.mp.dto.DishDTO;
import com.klayrocha.ifood.mp.model.Dish;

import io.smallrye.mutiny.Multi;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

	@Inject
	io.vertx.mutiny.pgclient.PgPool client;

	@GET
	@Path("{idRestaurant}/dishes")
	public Multi<DishDTO> buscarPratos(@PathParam("idRestaurant") Long idRestaurant) {
		return Dish.findAll(client, idRestaurant);
	}
}