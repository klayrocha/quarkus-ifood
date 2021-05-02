package com.klayrocha.ifood.mp.model;

import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

public class Restaurant {

	public Long id;

	public String name;

	public Localization localization;

	public void persist(PgPool pgPool) {
		pgPool.preparedQuery("insert into localization (id, latitude, longitude) values ($1, $2, $3)")
				.execute(Tuple.of(localization.id, localization.latitude, localization.longitude)).await()
				.indefinitely();

		pgPool.preparedQuery("insert into restaurant (id, name, localization_id) values ($1, $2, $3)")
				.execute(Tuple.of(id, name, localization.id)).await().indefinitely();

	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", localization=" + localization + "]";
	}

}
