package com.tfg.bangbangtan.restaurantapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tfg.bangbangtan.restaurantapp.Models.Dish;
import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;
import com.tfg.bangbangtan.restaurantapp.Repositories.DishRepository;
import com.tfg.bangbangtan.restaurantapp.Repositories.ExtraIngredientRepository;

import java.util.List;


public class DishDetailViewModel extends AndroidViewModel {

	private DishRepository dishRepository;
	private ExtraIngredientRepository extraIngredientRepository;

	public DishDetailViewModel(@NonNull Application application) {
		super(application);
		dishRepository = new DishRepository();
		extraIngredientRepository = new ExtraIngredientRepository();
	}

	public LiveData<Dish> getDish(int dishId) {
		return dishRepository.getDish(dishId);
	}

	public LiveData<List<ExtraIngredient>> getExtraIngredients(int dishType) {
		return extraIngredientRepository.getExtraIngredients(dishType);
	}
}

