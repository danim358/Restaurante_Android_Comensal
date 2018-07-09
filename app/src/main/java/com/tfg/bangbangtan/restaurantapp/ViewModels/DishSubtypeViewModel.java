package com.tfg.bangbangtan.restaurantapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tfg.bangbangtan.restaurantapp.Models.DishSubtype;
import com.tfg.bangbangtan.restaurantapp.Repositories.DishSubtypeRepository;

import java.util.List;

public class DishSubtypeViewModel extends AndroidViewModel{

	private DishSubtypeRepository dishSubtypeRepository;

	public DishSubtypeViewModel(@NonNull Application application) {
		super(application);
		dishSubtypeRepository = new DishSubtypeRepository();
	}

	public LiveData<List<DishSubtype>> getDishSubtypesList(int dishTypeId){
		return dishSubtypeRepository.getDishSubtypesList(dishTypeId);
	}
}
