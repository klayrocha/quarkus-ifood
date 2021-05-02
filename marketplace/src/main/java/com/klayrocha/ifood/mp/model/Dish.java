package com.klayrocha.ifood.mp.model;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import com.klayrocha.ifood.mp.dto.DishDTO;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class Dish {

	public Long id;

	public String name;

	public String description;

	public Restaurant restaurant;

	public BigDecimal price;

	public static Multi<DishDTO> findAll(PgPool pgPool) {
		Uni<RowSet<Row>> preparedQuery = pgPool.query("select * from dish").execute();
		return unitToMulti(preparedQuery);
	}

	public static Multi<DishDTO> findAll(PgPool client, Long idRestaurant) {
		Uni<RowSet<Row>> preparedQuery = client
				.preparedQuery("SELECT * FROM dish where dish.restaurant_id = $1 ORDER BY name ASC")
				.execute(Tuple.of(idRestaurant));
		return unitToMulti(preparedQuery);
	}

	private static Multi<DishDTO> unitToMulti(Uni<RowSet<Row>> queryResult) {
		return queryResult.onItem().transformToMulti(set -> Multi.createFrom().items(() -> {
			return StreamSupport.stream(set.spliterator(), false);
		})).onItem().transform(DishDTO::from);
	}

	public static Uni<DishDTO> findById(PgPool client, Long id) {
		return client.preparedQuery("SELECT * FROM dish WHERE id = $1").execute(Tuple.of(id)).map(RowSet::iterator)
				.map(iterator -> iterator.hasNext() ? DishDTO.from(iterator.next()) : null);
	}

}
