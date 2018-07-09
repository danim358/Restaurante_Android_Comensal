package com.tfg.bangbangtan.restaurantapp.Models;

import com.google.gson.annotations.SerializedName;

public class DishType extends MenuItem {
	private int id;
	@SerializedName("has_subtypes")
	private boolean hasSubtypes;

	public DishType(String image, String name, int id) {
		setImage(image);
		setName(name);
		this.id = id;
		this.hasSubtypes = false;
	}

	public int getId() {
		return id;
	}

	public boolean getHasSubtypes() {
		return hasSubtypes;
	}
}