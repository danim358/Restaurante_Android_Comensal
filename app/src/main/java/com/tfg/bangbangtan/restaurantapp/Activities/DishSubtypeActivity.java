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

import com.tfg.bangbangtan.restaurantapp.Models.DishSubtype;
import com.tfg.bangbangtan.restaurantapp.R;
import com.tfg.bangbangtan.restaurantapp.Utilities.AppString;
import com.tfg.bangbangtan.restaurantapp.Utilities.ItemMenuAdapter;
import com.tfg.bangbangtan.restaurantapp.ViewModels.DishSubtypeViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishSubtypeActivity extends AppCompatActivity {

	DishSubtypeViewModel dishSubtypeViewModel;
	List<DishSubtype> dishSubtypes;

	@BindView(R.id.recycler_list_view)
	RecyclerView dishSubtypesListView;
	ItemMenuAdapter<DishSubtype> itemMenuAdapter;

	@BindView(R.id.paymentButton)
	FloatingActionButton floatingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycler_list);
		ButterKnife.bind(this);
		dishSubtypes = new ArrayList<>();
		dishSubtypeViewModel = ViewModelProviders.of(this).get(DishSubtypeViewModel.class);

		itemMenuAdapter = new ItemMenuAdapter<>(dishSubtypes, this::onItemClick);
		dishSubtypesListView.setAdapter(itemMenuAdapter);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		dishSubtypesListView.setLayoutManager(linearLayoutManager);
		dishSubtypesListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		int dishTypeId = getIntent().getIntExtra(AppString.CLICKED_ITEM_ID, 0);

		floatingButton.setOnClickListener(this::onClickCartButton);

		loadDishSubtypes(dishTypeId);
	}

	private void onClickCartButton(View v) {
		Intent backToMenu;
		backToMenu = new Intent(DishSubtypeActivity.this, OrderActivity.class);
		startActivity(backToMenu);
	}

	private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent showDishes;
		showDishes = new Intent(DishSubtypeActivity.this, DishesActivity.class);
		showDishes.putExtra(AppString.DISH_TYPE_ID, dishSubtypes.get(position).getDishTypeId());
		showDishes.putExtra(AppString.DISH_SUBTYPE_ID, dishSubtypes.get(position).getId());
		startActivity(showDishes);
	}

	private void loadDishSubtypes(int dishTypeId) {
		final LiveData<List<DishSubtype>> dishSubtypesLiveData = dishSubtypeViewModel.getDishSubtypesList(dishTypeId);
		Observer<List<DishSubtype>> dishSubtypesResponseObserver = new DishSubtypeActivity.ObserverDishSubtypes(dishSubtypesLiveData);
		dishSubtypesLiveData.removeObservers(this);
		dishSubtypesLiveData.observe(DishSubtypeActivity.this, dishSubtypesResponseObserver);
	}

	private void updateDishSubtypesOnUI(List<DishSubtype> dishSubtypes) {
		this.dishSubtypes.clear();
		this.dishSubtypes.addAll(dishSubtypes);
		itemMenuAdapter.notifyDataSetChanged();
	}

	private class ObserverDishSubtypes implements Observer<List<DishSubtype>> {
		private LiveData<List<DishSubtype>> dishSubtypesLiveData;

		ObserverDishSubtypes(LiveData<List<DishSubtype>> dishSubtypesLiveData) {
			this.dishSubtypesLiveData = dishSubtypesLiveData;
		}

		@Override
		public void onChanged(@Nullable List<DishSubtype> dishTypes) {
			if (dishTypes != null) {
				updateDishSubtypesOnUI(dishTypes);
				dishSubtypesLiveData.removeObserver(this);
			}
		}
	}
}
