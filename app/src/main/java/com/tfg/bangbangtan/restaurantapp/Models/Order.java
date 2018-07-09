package com.tfg.bangbangtan.restaurantapp.Models;

import java.util.ArrayList;
import java.util.List;

public class Order {


	private int id;
	private int tableId;
	private List<CustomDish> customDishes;
	private double cost;
	private static Order instance;

	private Order(){

	customDishes = new ArrayList<>();
	cost=0;
	id=0;
	tableId=7; //TODO HACER QUE ESTO VENGA DEL USER DEL LOGIN
	}

	public static Order getInstance() {
		if (instance == null) {
			instance = new Order();
		}
		return instance;
	}

	public int getTableId() {
		return tableId;
	}

	public List<CustomDish> getCustomDishes() {
		return customDishes;
	}

	public void addCustomDish(CustomDish customDish) {
		this.customDishes.add(customDish);
		cost += customDish.getCost();
	}
	public void removeCustomDish(int position){
		double remaining=cost - customDishes.get(position).getCost();
		cost = remaining;
		this.customDishes.remove(position);
	}

	public double getCost() {
		return cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
