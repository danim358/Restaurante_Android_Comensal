package com.tfg.bangbangtan.restaurantapp.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tfg.bangbangtan.restaurantapp.Models.CustomDish;
import com.tfg.bangbangtan.restaurantapp.Models.Dish;
import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;
import com.tfg.bangbangtan.restaurantapp.Models.Order;
import com.tfg.bangbangtan.restaurantapp.R;
import com.tfg.bangbangtan.restaurantapp.Utilities.AppString;
import com.tfg.bangbangtan.restaurantapp.Utilities.ExtraIngredientAdapter;
import com.tfg.bangbangtan.restaurantapp.ViewModels.DishDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishDetailActivity extends AppCompatActivity {

	private DishDetailViewModel dishDetailViewModel;
	private Dish dish;
	private List<ExtraIngredient> extraIngredients;
	private ExtraIngredientAdapter extraIngredientAdapter;
	private double totalPrice;

	@BindView(R.id.add_dish_to_order_button)
	Button add_to_order;

	@BindView(R.id.dish_detail_img)
	ImageView dish_image;

	@BindView(R.id.dish_name_txt)
	TextView name_txt;

	@BindView(R.id.total_dish_price_txt)
	TextView total_price_txt;

	@BindView(R.id.dish_description_txt)
	TextView description_txt;

	@BindView(R.id.dish_price_txt)
	TextView price_txt;

	@BindView(R.id.dish_comment_input)
	TextInputEditText comment_input;

	@BindView(R.id.extra_ing_list_view)
	RecyclerView extra_ing_list_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dishes_detail);
		ButterKnife.bind(this);
		dishDetailViewModel = ViewModelProviders.of(this).get(DishDetailViewModel.class);
		extraIngredients = new ArrayList<>();

		extraIngredientAdapter = new ExtraIngredientAdapter(extraIngredients, new ExtraIngredientAdapter.OnSelectQuantityListener() {
			@Override
			public void onSelectQuantity(int position) {
				DishDetailActivity.this.onSelectExtraIngredientQuantity(position);
			}
		});
		extra_ing_list_view.setAdapter(extraIngredientAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		extra_ing_list_view.setLayoutManager(linearLayoutManager);

		final int dishId = getIntent().getIntExtra(AppString.CLICKED_ITEM_ID, 0);
		int typeId = getIntent().getIntExtra(AppString.DISH_TYPE_ID, 0);
		loadDish(dishId);
		loadExtraIngredients(typeId);

		add_to_order.setOnClickListener(this::onClickAddToOrder);
	}

	private void onSelectExtraIngredientQuantity(int position) {
		totalPrice = dish.getPrice();
		for (ExtraIngredient extraIngredient : extraIngredients) {
			totalPrice += extraIngredient.getQuantity() * extraIngredient.getPrice();
		}
		total_price_txt.setText(String.format("%.2f €", totalPrice));
	}

	private void loadDish(int dishId) {
		final LiveData<Dish> dishLiveData = dishDetailViewModel.getDish(dishId);
		Observer<Dish> dishResponseObserver = new ObserverDish(dishLiveData);
		dishLiveData.removeObservers(this);
		dishLiveData.observe(DishDetailActivity.this, dishResponseObserver);
	}

	private void loadExtraIngredients(int dishTypeId) {
		final LiveData<List<ExtraIngredient>> extraIngredientsLiveData = dishDetailViewModel.getExtraIngredients(dishTypeId);
		Observer<List<ExtraIngredient>> extraIngredientsResponseObserver = new ObserverExtraIngredients(extraIngredientsLiveData);
		extraIngredientsLiveData.removeObservers(this);
		extraIngredientsLiveData.observe(DishDetailActivity.this, extraIngredientsResponseObserver);
	}

	private void updateDishes(Dish dish) {
		this.dish = dish;
		setDishDataOnUI(dish);
	}

	private void setDishDataOnUI(Dish dish) {
		Picasso.get().load(dish.getImage()).into(dish_image);
		name_txt.setText(dish.getName());
		description_txt.setText(dish.getDescription());
		totalPrice = dish.getPrice();
		price_txt.setText(String.format("%s€", dish.getPrice()));
	}

	private void updateExtraIngredients(List<ExtraIngredient> extraIngredients) {
		this.extraIngredients.clear();
		this.extraIngredients.addAll(extraIngredients);
		extraIngredientAdapter.notifyDataSetChanged();
	}

	private void onClickAddToOrder(View v) {
		if (dish != null) {
			CustomDish customDish = new CustomDish(dish, totalPrice, comment_input.getText().toString());
			customDish.setSelectedExtraIngredients(extraIngredients);
			Order.getInstance().addCustomDish(customDish);

			Toast.makeText(DishDetailActivity.this, "Se ha añadido el plato", Toast.LENGTH_LONG).show();
			Intent backToMenu;
			backToMenu = new Intent(DishDetailActivity.this, DishTypeActivity.class);
			startActivity(backToMenu);
		}
	}

	private class ObserverDish implements Observer<Dish> {
		private LiveData<Dish> dishLiveData;

		ObserverDish(LiveData<Dish> dishLiveData) {
			this.dishLiveData = dishLiveData;
		}

		@Override
		public void onChanged(@Nullable Dish dish) {
			if (dish != null) {
				updateDishes(dish);
				dishLiveData.removeObserver(this);
			}
		}
	}

	private class ObserverExtraIngredients implements Observer<List<ExtraIngredient>> {

		private LiveData<List<ExtraIngredient>> extraIngredientsLiveData;

		ObserverExtraIngredients(LiveData<List<ExtraIngredient>> extraIngredientsLiveData) {
			this.extraIngredientsLiveData = extraIngredientsLiveData;
		}

		@Override
		public void onChanged(@Nullable List<ExtraIngredient> extraIngredients) {
			if (extraIngredients != null) {
				updateExtraIngredients(extraIngredients);
				extraIngredientsLiveData.removeObserver(this);
			}
		}
	}
}

