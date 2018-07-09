package com.tfg.bangbangtan.restaurantapp.Repositories;

import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.tfg.bangbangtan.restaurantapp.API_Management.APIManager;
import com.tfg.bangbangtan.restaurantapp.Activities.OrderActivity;
import com.tfg.bangbangtan.restaurantapp.Models.Order;

public class OrderRepository {

	public MutableLiveData<Order> createOrder(){
		final MutableLiveData<Order> responseLiveData= new MutableLiveData<>();
		APIManager.getInstance().createOrder( new APIManager.ResponseCallback<Order>() {
			@Override
			public void OnResponseSuccess(Order responseObject) {
				responseLiveData.setValue(responseObject);

			}

			@Override
			public void OnResponseFailure() {

			}
		});
		return responseLiveData;
	}
}
