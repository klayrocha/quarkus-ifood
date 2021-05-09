package com.klayrocha.ifood.pedido.conf;

import com.klayrocha.ifood.pedido.dto.OrderCompletedDTO;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderDeserializer extends ObjectMapperDeserializer<OrderCompletedDTO> {

	public OrderDeserializer() {
		super(OrderCompletedDTO.class);
	}

}
