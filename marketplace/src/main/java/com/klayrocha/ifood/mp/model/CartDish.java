package com.klayrocha.ifood.mp.model;

import java.util.ArrayList;
import java.util.List;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class CartDish {

	public String customer;

	public Long dish;

	public static Uni<Long> save(PgPool client, String cliente, Long prato) {
		return client.preparedQuery("INSERT INTO prato_cliente (cliente, prato) VALUES ($1, $2) RETURNING (cliente)")
				.execute(Tuple.of(cliente, prato))

				.map(pgRowSet -> pgRowSet.iterator().next().getLong("cliente"));
	}

	public static Uni<List<CartDish>> findCarrinho(PgPool client, String cliente) {
		return client.preparedQuery("select * from prato_cliente where cliente = $1 ").execute(Tuple.of(cliente))
				.map(pgRowSet -> {
					List<CartDish> list = new ArrayList<>(pgRowSet.size());
					for (Row row : pgRowSet) {
						list.add(toPratoCarrinho(row));
					}
					return list;
				});
	}

	private static CartDish toPratoCarrinho(Row row) {
		CartDish pc = new CartDish();
		pc.customer = row.getString("cliente");
		pc.dish = row.getLong("prato");
		return pc;
	}

	public static Uni<Boolean> delete(PgPool client, String cliente) {
		return client.preparedQuery("DELETE FROM prato_cliente WHERE cliente = $1").execute(Tuple.of(cliente))
				.map(pgRowSet -> pgRowSet.rowCount() == 1);

	}

}
