package com.tfg.bangbangtan.restaurantapp.Repositories;

import android.arch.lifecycle.MutableLiveData;

import com.tfg.bangbangtan.restaurantapp.API_Management.APIManager;
import com.tfg.bangbangtan.restaurantapp.Models.Dish;

import java.util.List;

public class DishRepository {
    public MutableLiveData<Dish> getDish(int dishId) {
        final MutableLiveData<Dish> responseLiveData = new MutableLiveData<>();

        APIManager.getInstance().getDish(dishId, new APIManager.ResponseCallback<Dish>() {
            @Override
            public void OnResponseSuccess(Dish responseObject) {
                responseLiveData.setValue(responseObject);
            }

            @Override
            public void OnResponseFailure() {
            }
        });
        return responseLiveData;
    }
    public MutableLiveData<List<Dish>> getDishes(int dishTypeId, int dishSubtypeId) {
        final MutableLiveData<List<Dish>> responseLiveData = new MutableLiveData<>();
        APIManager.getInstance().getDishes(dishTypeId, dishSubtypeId, new APIManager.ResponseCallback<List<Dish>>() {
            @Override
            public void OnResponseSuccess(List<Dish> responseObject) {
                responseLiveData.setValue(responseObject);
            }

            @Override
            public void OnResponseFailure() {
            }
        });
        return responseLiveData;
    }
}