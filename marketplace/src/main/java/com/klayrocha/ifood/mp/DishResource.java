package com.klayrocha.ifood.mp;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.klayrocha.ifood.mp.dto.DishDTO;
import com.klayrocha.ifood.mp.model.Dish;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;


@Path("/dishes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DishResource {
	
	@Inject
    PgPool pgPool;

	@GET
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = DishDTO.class)))
    public Multi<DishDTO> findDish() {
        return Dish.findAll(pgPool);
    }
}