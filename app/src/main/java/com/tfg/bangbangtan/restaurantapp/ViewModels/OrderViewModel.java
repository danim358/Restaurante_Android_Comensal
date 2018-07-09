package com.tfg.bangbangtan.restaurantapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tfg.bangbangtan.restaurantapp.Models.CustomDish;
import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;
import com.tfg.bangbangtan.restaurantapp.Models.Order;
import com.tfg.bangbangtan.restaurantapp.Repositories.CustomDishRepository;
import com.tfg.bangbangtan.restaurantapp.Repositories.OrderRepository;


public class OrderViewModel extends AndroidViewModel {

	private CustomDishRepository customDishRepository;
	private OrderRepository orderRepository;

	public OrderViewModel(@NonNull Application application) {
		super(application);
		customDishRepository = new CustomDishRepository();
		orderRepository = new OrderRepository();
	}

	public LiveData<CustomDish> createCustomDish(CustomDish customDish) {
		return customDishRepository.createCustomDish(customDish, customDish.getSelectedExtraIngredients());
	}

	public LiveData<Order> createOrder() {
		return orderRepository.createOrder();
	}
}

