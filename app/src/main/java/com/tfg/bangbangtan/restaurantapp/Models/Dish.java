package com.tfg.bangbangtan.restaurantapp.Models;

import com.google.gson.annotations.SerializedName;

public class Dish extends MenuItem {
	private int id;
	private String description;
	@SerializedName("dish_type_id")
	private int dishTypeId;
	@SerializedName("dish_subtype_id")
	private int dishSubtypeId;
	private double price;


	public Dish(String name, String image, String description, int dishTypeId, int dishSubtypeId, double price){
		this.description = description;
		this.dishTypeId = dishTypeId;
		this.dishSubtypeId = dishSubtypeId;
		this.price=price;
		setName(name);
		setImage(image);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDishTypeId() {
		return dishTypeId;
	}


	public int getDishSubtypeId() {
		return dishSubtypeId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}


}
