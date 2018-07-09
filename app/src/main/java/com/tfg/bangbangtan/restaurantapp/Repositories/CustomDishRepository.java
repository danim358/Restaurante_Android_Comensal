package com.tfg.bangbangtan.restaurantapp.Repositories;

import android.arch.lifecycle.MutableLiveData;

import com.tfg.bangbangtan.restaurantapp.API_Management.APIManager;
import com.tfg.bangbangtan.restaurantapp.Models.CustomDish;
import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;

import java.util.List;

public class CustomDishRepository {

	public MutableLiveData<CustomDish> createCustomDish(CustomDish customDish, List<ExtraIngredient> extraIngredients){
		final MutableLiveData<CustomDish> responseLiveData= new MutableLiveData<>();
		APIManager.getInstance().createCustomDish(customDish, new APIManager.ResponseCallback<CustomDish>() {
			@Override
			public void OnResponseSuccess(CustomDish responseObject) {
				responseLiveData.setValue(responseObject);
				if(extraIngredients != null) {
					for (ExtraIngredient extraIngredient : extraIngredients) {
						addExtraIngredient(responseObject, extraIngredient);
					}
				}
			}

			@Override
			public void OnResponseFailure() {
			}
		});
		return responseLiveData;
	}

	private void addExtraIngredient(CustomDish customDish, ExtraIngredient extraIngredient){
		APIManager.getInstance().addExtraIngredientToCustomDish(customDish, extraIngredient, new APIManager.ResponseCallback<List<ExtraIngredient>>() {
			@Override
			public void OnResponseSuccess(List<ExtraIngredient> responseObject) {}

			@Override
			public void OnResponseFailure() {}
		});
	}
}
