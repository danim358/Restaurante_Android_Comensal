package com.tfg.bangbangtan.restaurantapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomDish {

	private int id;
	@SerializedName("order_id")
	private int orderId;
	@SerializedName("dish_id")
	private int dishId;
	private double cost;
	private String comment;

	private Dish dish;


	private List<ExtraIngredient> selectedExtraIngredients;

	public CustomDish(Dish dish, double cost, String comment)
	{
		this.selectedExtraIngredients= new ArrayList<>();
		this.dish = dish;
		this.dishId = dish.getId();
		this.cost = cost;
		this.comment = comment;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getDishId() {
		return dishId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setSelectedExtraIngredients(List<ExtraIngredient> extraIngredients){
		for(ExtraIngredient extraIngredient: extraIngredients){
			if(extraIngredient.getQuantity()>0){
				this.selectedExtraIngredients.add(extraIngredient);
			}
		}
	}
	public List<ExtraIngredient> getSelectedExtraIngredients() {
		return selectedExtraIngredients;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}
}
