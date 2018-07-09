package com.tfg.bangbangtan.restaurantapp.API_Management;

import android.support.annotation.NonNull;

import com.tfg.bangbangtan.restaurantapp.Models.CustomDish;
import com.tfg.bangbangtan.restaurantapp.Models.Dish;
import com.tfg.bangbangtan.restaurantapp.Models.DishSubtype;
import com.tfg.bangbangtan.restaurantapp.Models.DishType;
import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;
import com.tfg.bangbangtan.restaurantapp.Models.Order;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

	private static final String API_URL = "http://35.176.234.214/api/";
	private static APIManager instance;
	private static RestaurantService restaurantService;

	private APIManager() {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
				Request request = chain.request().newBuilder().addHeader("api-key", "$2y$10$GOl48sNKz0G6fswLOo53c.sx78mHHc1V6.JhJ8tCBWFoSyrZrVcW2").build();
				return chain.proceed(request);
			}
		});

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(API_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient.build())
				.build();

		restaurantService = retrofit.create(RestaurantService.class);
	}

	public static APIManager getInstance() {
		if (instance == null) {
			instance = new APIManager();
		}
		return instance;
	}

	public void getDishSubtypes(int dishTypeId, final ResponseCallback<List<DishSubtype>> responseCallback) {
		Call<List<DishSubtype>> subtypesCall = restaurantService.getSubtypes(dishTypeId);
		subtypesCall.enqueue(new Callback<List<DishSubtype>>() {
			@Override
			public void onResponse(Call<List<DishSubtype>> call, Response<List<DishSubtype>> response) {
				responseCallback.OnResponseSuccess(response.body());
			}

			@Override
			public void onFailure(Call<List<DishSubtype>> call, Throwable t) {
				responseCallback.OnResponseFailure();
			}
		});
	}

	public void getDishTypes(final ResponseCallback<List<DishType>> responseCallback) {
		Call<List<DishType>> typesCall = restaurantService.getAllDishTypes();

		typesCall.enqueue(new Callback<List<DishType>>() {
			@Override
			public void onResponse(Call<List<DishType>> call, Response<List<DishType>> response) {
				responseCallback.OnResponseSuccess(response.body());
			}

			@Override
			public void onFailure(Call<List<DishType>> call, Throwable t) {
				responseCallback.OnResponseFailure();
			}
		});
	}

	public void getRelatedExtras(int dishTypeId, final ResponseCallback<List<ExtraIngredient>> responseCallback) {

		Call<List<ExtraIngredient>> extrasCall = restaurantService.getRelatedExtras(dishTypeId);
		extrasCall.enqueue(new Callback<List<ExtraIngredient>>() {
			@Override
			public void onResponse(Call<List<ExtraIngredient>> call, Response<List<ExtraIngredient>> response) {
				responseCallback.OnResponseSuccess(response.body());
			}

			@Override
			public void onFailure(Call<List<ExtraIngredient>> call, Throwable t) {
				responseCallback.OnResponseFailure();
			}
		});
	}

	public void getDishes(int dishTypeId, int dishSubtypeId, final ResponseCallback<List<Dish>> responseCallback) {
		Call<List<Dish>> dishesCall;
		if (dishSubtypeId == 0) {
			dishesCall = restaurantService.getDishesFromType(dishTypeId);
		} else {
			dishesCall = restaurantService.getDishesFromSubtype(dishSubtypeId);
		}

		dishesCall.enqueue(new Callback<List<Dish>>() {
			@Override
			public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
				responseCallback.OnResponseSuccess(response.body());
			}

			@Override
			public void onFailure(Call<List<Dish>> call, Throwable t) {
				responseCallback.OnResponseFailure();
			}
		});
	}

	public void getDish(int dishId, final ResponseCallback<Dish> responseCallback) {
		Call<Dish> dishCall = restaurantService.getDish(dishId);

		dishCall.enqueue(new Callback<Dish>() {
			@Override
			public void onResponse(Call<Dish> call, Response<Dish> response) {
				responseCallback.OnResponseSuccess(response.body());
			}

			@Override
			public void onFailure(Call<Dish> call, Throwable t) {
				responseCallback.OnResponseFailure();
			}
		});
	}

	public void getOrder(int orderId) {
		Call<Order> orderCall = restaurantService.getOrder(orderId);
		orderCall.enqueue(new Callback<Order>() {
			@Override
			public void onResponse(Call<Order> call, Response<Order> response) {
				Order.getInstance().setId(response.body().getId());
			}

			@Override
			public void onFailure(Call<Order> call, Throwable t) {

			}
		});
	}

	public void createOrder(final ResponseCallback<Order> responseCallback) {
		Call<Order> orderCall = restaurantService.createOrder(Order.getInstance().getCost(), Order.getInstance().getTableId());
		orderCall.enqueue(new Callback<Order>() {
			@Override
			public void onResponse(Call<Order> call, Response<Order> response) {
				if (response.code() == 201) {
					responseCallback.OnResponseSuccess(response.body());
				} else {
					responseCallback.OnResponseFailure();
				}
			}

			@Override
			public void onFailure(Call<Order> call, Throwable t) {
				responseCallback.OnResponseFailure();
			}
		});
	}

	public void createCustomDish(final CustomDish customDish, final ResponseCallback<CustomDish> responseCallback) {
		if (Order.getInstance().getId() != 0) {

			Call<CustomDish> customDishCall = restaurantService.createCustomDish(customDish.getOrderId(), customDish.getComment().isEmpty() ? null : customDish.getComment(), customDish.getCost(), customDish.getDishId());

			customDishCall.enqueue(new Callback<CustomDish>() {
				@Override
				public void onResponse(Call<CustomDish> call, Response<CustomDish> response) {
					if (response.code() == 201) {
						responseCallback.OnResponseSuccess(response.body());
					} else {
						responseCallback.OnResponseFailure();
					}
				}

				@Override
				public void onFailure(Call<CustomDish> call, Throwable t) {
					responseCallback.OnResponseFailure();
				}
			});
		}
	}

	public void addExtraIngredientToCustomDish(final CustomDish customDish, final ExtraIngredient extraIngredient, final ResponseCallback<List<ExtraIngredient>> responseCallback) {
		Call<List<ExtraIngredient>> extraCall = restaurantService.addExtraIngredientToCustomDish(Order.getInstance().getId(), customDish.getId(), extraIngredient.getId(), extraIngredient.getQuantity());
		extraCall.enqueue(new Callback<List<ExtraIngredient>>() {
			@Override
			public void onResponse(Call<List<ExtraIngredient>> call, Response<List<ExtraIngredient>> response) {
				if(response.code() == 200){
					responseCallback.OnResponseSuccess(response.body());
				}
				else{
					responseCallback.OnResponseFailure();
				}
			}

			@Override
			public void onFailure(Call<List<ExtraIngredient>> call, Throwable t) {
				responseCallback.OnResponseFailure();
			}
		});
	}

	public interface ResponseCallback<T> {
		void OnResponseSuccess(T responseObject);
		void OnResponseFailure();
	}
}
