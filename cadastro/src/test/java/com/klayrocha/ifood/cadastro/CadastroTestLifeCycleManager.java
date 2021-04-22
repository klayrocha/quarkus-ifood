package com.klayrocha.ifood.cadastro;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class CadastroTestLifeCycleManager implements QuarkusTestResourceLifecycleManager {

	private static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:12.2");

	@Override
	public Map<String, String> start() {
		POSTGRES.start();

		Map<String, String> properties = new HashMap<>();

		properties.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
		properties.put("quarkus.datasource.username", POSTGRES.getUsername());
		properties.put("quarkus.datasource.password", POSTGRES.getPassword());
		return properties;
	}

	@Override
	public void stop() {
		if (POSTGRES != null) {
			POSTGRES.stop();
		}
	}

}
