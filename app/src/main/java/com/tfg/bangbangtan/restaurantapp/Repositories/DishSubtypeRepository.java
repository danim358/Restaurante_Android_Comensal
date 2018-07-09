package com.tfg.bangbangtan.restaurantapp.Repositories;

import android.arch.lifecycle.MutableLiveData;

import com.tfg.bangbangtan.restaurantapp.API_Management.APIManager;
import com.tfg.bangbangtan.restaurantapp.Models.DishSubtype;

import java.util.List;

public class DishSubtypeRepository {

	public MutableLiveData<List<DishSubtype>> getDishSubtypesList(int dishTypeId) {
		final MutableLiveData<List<DishSubtype>> responseLiveData = new MutableLiveData<>();

		APIManager.getInstance().getDishSubtypes(dishTypeId, new APIManager.ResponseCallback<List<DishSubtype>>() {
			@Override
			public void OnResponseSuccess(List<DishSubtype> responseObject) {
				responseLiveData.setValue(responseObject);
			}

			@Override
			public void OnResponseFailure() {
			}
		});

		return responseLiveData;
	}
}
