package com.tfg.bangbangtan.restaurantapp.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tfg.bangbangtan.restaurantapp.Models.Dish;
import com.tfg.bangbangtan.restaurantapp.R;
import com.tfg.bangbangtan.restaurantapp.Utilities.AppString;
import com.tfg.bangbangtan.restaurantapp.Utilities.ItemMenuAdapter;
import com.tfg.bangbangtan.restaurantapp.ViewModels.DishesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishesActivity extends AppCompatActivity {

	DishesViewModel dishesViewModel;
	List<Dish> dishes;

	@BindView(R.id.recycler_list_view)
	RecyclerView dishesListView;
	ItemMenuAdapter<Dish> itemMenuAdapter;
	private int dishTypeId;

	@BindView(R.id.paymentButton)
	FloatingActionButton floatingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycler_list);
		ButterKnife.bind(this);
		dishes = new ArrayList<>();
		dishesViewModel = ViewModelProviders.of(this).get(DishesViewModel.class);

		itemMenuAdapter = new ItemMenuAdapter<>(dishes, this::onItemClick);
		dishesListView.setAdapter(itemMenuAdapter);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		dishesListView.setLayoutManager(linearLayoutManager);
		dishesListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		floatingButton.setOnClickListener(this::onClickCartButton);

		dishTypeId = getIntent().getIntExtra(AppString.DISH_TYPE_ID, 0);
		int dishSubtypeId = getIntent().getIntExtra(AppString.DISH_SUBTYPE_ID, 0);
		loadDishes(dishTypeId, dishSubtypeId);
	}

	private void onClickCartButton(View v) {
		Intent backToMenu;
		backToMenu = new Intent(DishesActivity.this, OrderActivity.class);
		startActivity(backToMenu);
	}

	private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(DishesActivity.this, "Click en Dish " + position, Toast.LENGTH_SHORT).show();
		Intent showDetail;
		showDetail = new Intent( DishesActivity.this, DishDetailActivity.class);
		int dishId = dishes.get(position).getId();
		showDetail.putExtra(AppString.CLICKED_ITEM_ID, dishId);
		showDetail.putExtra(AppString.DISH_TYPE_ID, dishTypeId);
		startActivity(showDetail);
	}

	private void loadDishes(int dishTypeId, int dishSubtypeId) {
		final LiveData<List<Dish>> dishesLiveData = dishesViewModel.getDishes(dishTypeId, dishSubtypeId);
		Observer<List<Dish>> dishesResponseObserver = new DishesActivity.ObserverDishes(dishesLiveData);
		dishesLiveData.removeObservers(this);
		dishesLiveData.observe(DishesActivity.this, dishesResponseObserver);
	}

	private void updateDishesOnUI(List<Dish> dishes) {
		this.dishes.clear();
		this.dishes.addAll(dishes);
		itemMenuAdapter.notifyDataSetChanged();
	}

	private class ObserverDishes implements Observer<List<Dish>> {
		private LiveData<List<Dish>> dishesLiveData;

		ObserverDishes(LiveData<List<Dish>> dishesLiveData) {
			this.dishesLiveData = dishesLiveData;
		}

		@Override
		public void onChanged(@Nullable List<Dish> dishes) {
			if (dishes != null) {
				updateDishesOnUI(dishes);
				dishesLiveData.removeObserver(this);
			}
		}
	}
}
