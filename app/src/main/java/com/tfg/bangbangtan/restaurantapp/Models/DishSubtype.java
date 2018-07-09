package com.tfg.bangbangtan.restaurantapp.Models;

import com.google.gson.annotations.SerializedName;

public class DishSubtype extends MenuItem {
	private int id;
	@SerializedName("dish_type_id")
	private int dishTypeId;

	public DishSubtype(String image, String name, int id, int dishTypeId) {
		this.id = id;
		setImage(image);
		setName(name);
		this.dishTypeId = dishTypeId;
	}

	public int getId() {
		return id;
	}

	public int getDishTypeId() {
		return dishTypeId;
	}
}
