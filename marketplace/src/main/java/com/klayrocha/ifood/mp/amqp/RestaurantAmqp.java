package com.klayrocha.ifood.mp.amqp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.klayrocha.ifood.mp.model.Restaurant;

import io.vertx.mutiny.pgclient.PgPool;

@ApplicationScoped
public class RestaurantAmqp {

	@Inject
	PgPool pgPool;

	@Incoming("restaurant")
	public void receberRestaurante(String json) {
		Jsonb create = JsonbBuilder.create();
		Restaurant restaurant = create.fromJson(json, Restaurant.class);
		System.out.println("###### Chegou aqui " + restaurant.toString());
		restaurant.persist(pgPool);
	}
}
