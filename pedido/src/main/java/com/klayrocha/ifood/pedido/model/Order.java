package com.klayrocha.ifood.pedido.model;

import java.util.List;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection = "orders", database = "admin")
public class Order extends PanacheMongoEntity {

    public String customer;

    public List<Dish> dishes;

    public Restaurant restaurant;

    public String deliveryman;

    public Localization localizationDeliveryman;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getDeliveryman() {
		return deliveryman;
	}

	public void setDeliveryman(String deliveryman) {
		this.deliveryman = deliveryman;
	}

	public Localization getLocalizationDeliveryman() {
		return localizationDeliveryman;
	}

	public void setLocalizationDeliveryman(Localization localizationDeliveryman) {
		this.localizationDeliveryman = localizationDeliveryman;
	}
    
    

}