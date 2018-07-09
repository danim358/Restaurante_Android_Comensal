package com.tfg.bangbangtan.restaurantapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tfg.bangbangtan.restaurantapp.Models.Dish;
import com.tfg.bangbangtan.restaurantapp.Repositories.DishRepository;

import java.util.List;

public class DishesViewModel extends AndroidViewModel {

	private DishRepository dishRepository;

	public DishesViewModel(@NonNull Application application) {
		super(application);
		dishRepository = new DishRepository();
	}

	public LiveData<List<Dish>> getDishes(int dishTypeId, int dishSubtypeId){
		return dishRepository.getDishes(dishTypeId, dishSubtypeId);
	}
}
