package com.klayrocha.ifood.mp;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.smallrye.mutiny.Uni;

import com.klayrocha.ifood.mp.dto.DishDTO;
import com.klayrocha.ifood.mp.dto.DishOrdeerDTO;
import com.klayrocha.ifood.mp.dto.OrderCompletedDTO;
import com.klayrocha.ifood.mp.dto.RestaurantDTO;
import com.klayrocha.ifood.mp.model.CartDish;
import com.klayrocha.ifood.mp.model.Dish;




@Path("cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private static final String CUSTOMER = "a";

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;
    
    @Inject
    @Channel("orders")
    Emitter<OrderCompletedDTO> emitterOrder;
	
	@GET
    public Uni<List<CartDish>> buscarcarrinho() {
        return CartDish.findCarrinho(client, CUSTOMER);
    }

    @POST
    @Path("/prato/{idPrato}")
    public Uni<Long> adicionarPrato(@PathParam("idPrato") Long idDish) {
        CartDish pc = new CartDish();
        pc.customer = CUSTOMER;
        pc.dish = idDish;
        return CartDish.save(client, CUSTOMER, idDish);

    }

    @POST
    @Path("/realizar-pedido")
    public Uni<Boolean> finalizar() {
        OrderCompletedDTO pedido = new OrderCompletedDTO();
        pedido.customer = CUSTOMER;
        List<CartDish> pratoCarrinho = CartDish.findCarrinho(client, CUSTOMER).await().indefinitely();

        List<DishOrdeerDTO> dishes = pratoCarrinho.stream().map(pc -> from(pc)).collect(Collectors.toList());
        pedido.dishes = dishes;

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.name = "nome restaurante";
        pedido.restaurant = restaurant;
        emitterOrder.send(pedido);
        return CartDish.delete(client, CUSTOMER);
    }

    private DishOrdeerDTO from(CartDish cartDish) {
        DishDTO dto = Dish.findById(client, cartDish.dish).await().indefinitely();
        return new DishOrdeerDTO(dto.name, dto.description, dto.price);
    }
}
