package com.klayrocha.ifood.pedido.kafka;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.klayrocha.ifood.pedido.dto.DishOrdeerDTO;
import com.klayrocha.ifood.pedido.dto.OrderCompletedDTO;
import com.klayrocha.ifood.pedido.model.Dish;
import com.klayrocha.ifood.pedido.model.Order;
import com.klayrocha.ifood.pedido.model.Restaurant;

@ApplicationScoped
public class OrderCompletedIncoming {
	
	//@Inject
    //ESService elastic;

    @Incoming("orders")
    public void lerPedidos(OrderCompletedDTO dto) {
        System.out.println("-----------------");
        System.out.println(dto);

        Order p = new Order();
        System.out.println("DEBUG 2 -----------------");
        System.out.println("DEBUG 2.1 alterado -----------------"+dto.customer);
        p.setCustomer(dto.customer); //= "abbb";
        System.out.println("DEBUG 3 -----------------");
        p.setDishes(new ArrayList<>());
        System.out.println("DEBUG 4 -----------------");
        dto.dishes.forEach(dish -> p.getDishes().add(from(dish)));
        System.out.println("DEBUG 5 -----------------");
        Restaurant restaurant = new Restaurant();
        System.out.println("DEBUG 6 -----------------");
        restaurant.name = dto.restaurant.name;
        System.out.println("DEBUG 7 -----------------");
        p.setRestaurant(restaurant);
        System.out.println("DEBUG 8 -----------------");
        //String json = JsonbBuilder.create().toJson(dto);
        //elastic.index("pedidos", json);
        System.out.println("DEBUG 9 -----------------");
        p.persist();
        System.out.println("--------SALVOUUUUUU---------");
    }

    private Dish from(DishOrdeerDTO dish) {
        Dish p = new Dish();
        p.description = dish.description;
        p.name = dish.name;
        p.price = new Decimal128(dish.price);
        return p;
    }

}
