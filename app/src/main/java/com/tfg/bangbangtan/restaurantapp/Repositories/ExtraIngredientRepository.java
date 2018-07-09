package com.tfg.bangbangtan.restaurantapp.Repositories;

import android.arch.lifecycle.MutableLiveData;

import com.tfg.bangbangtan.restaurantapp.API_Management.APIManager;
import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;

import java.util.List;

public class ExtraIngredientRepository {

	public MutableLiveData<List<ExtraIngredient>> getExtraIngredients(int dishTypeId){
		final MutableLiveData<List<ExtraIngredient>> responseLiveData = new MutableLiveData<>();
		APIManager.getInstance().getRelatedExtras(dishTypeId, new APIManager.ResponseCallback<List<ExtraIngredient>>() {
			@Override
			public void OnResponseSuccess(List<ExtraIngredient> responseObject) {
				responseLiveData.setValue(responseObject);
			}

			@Override
			public void OnResponseFailure() {

			}
		});
		return responseLiveData;
	}
}
