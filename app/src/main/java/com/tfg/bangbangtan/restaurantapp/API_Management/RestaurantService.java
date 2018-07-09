package com.tfg.bangbangtan.restaurantapp.API_Management;

import com.tfg.bangbangtan.restaurantapp.Models.CustomDish;
import com.tfg.bangbangtan.restaurantapp.Models.Dish;
import com.tfg.bangbangtan.restaurantapp.Models.DishSubtype;
import com.tfg.bangbangtan.restaurantapp.Models.DishType;
import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;
import com.tfg.bangbangtan.restaurantapp.Models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantService {


	@GET("dishTypes/{dishType}/extraIngredients")
	Call<List<ExtraIngredient>> getRelatedExtras(@Path("dishType") int dishTypeId);


	@GET("dishTypes/{main_item_id}/dishSubtypes")
	Call<List<DishSubtype>> getSubtypes(@Path("main_item_id") int dishTypeId);


	@GET("dishTypes")
	Call<List<DishType>> getAllDishTypes();


	@GET("dishTypes/{dishTypeId}/dishes")
	Call<List<Dish>> getDishesFromType(@Path("dishTypeId") int dishTypeId);


	@GET("dishSubtypes/{dishSubtypeId}/dishes")
	Call<List<Dish>> getDishesFromSubtype( @Path("dishSubtypeId") int dishSubtypeId);


	@GET("dishes/{dishId}")
	Call<Dish> getDish(@Path("dishId") int dishId);

	@GET("orders/{orderId}")
	Call<Order> getOrder(@Path("orderId") int orderId);

	@FormUrlEncoded
	@POST("orders/{orderId}/customDishes")
	Call<CustomDish> createCustomDish(@Path("orderId") int order_id,
	                                  @Field("comment") String comment,
	                                  @Field("cost") Double cost,
	                                  @Field("dish_id") int dish_id);


	@FormUrlEncoded
	@PUT("orders/{order}/customDishes/{customDish}/extraIngredients/{extraIngredient}")
	Call<List<ExtraIngredient>> addExtraIngredientToCustomDish(@Path("order") int order,
	                                                           @Path("customDish") int customDish,
	                                                           @Path("extraIngredient") int extraIngredient,
	                                                           @Field("quantity") int quantity);

	@FormUrlEncoded
	@POST("orders")
	Call<Order> createOrder(@Field("cost") Double cost,
	                        @Field("table_id") int table_id);
}
