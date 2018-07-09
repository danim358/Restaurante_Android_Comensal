package com.tfg.bangbangtan.restaurantapp.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tfg.bangbangtan.restaurantapp.Models.DishType;
import com.tfg.bangbangtan.restaurantapp.R;
import com.tfg.bangbangtan.restaurantapp.Utilities.AppString;
import com.tfg.bangbangtan.restaurantapp.Utilities.ItemMenuAdapter;
import com.tfg.bangbangtan.restaurantapp.ViewModels.DishTypeViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishTypeActivity extends AppCompatActivity {


	DishTypeViewModel dishTypeViewModel;
	List<DishType> dishTypes;

	//@BindView(R.id.mainDishTypeList)
	@BindView(R.id.recycler_list_view)
	RecyclerView dishTypesListView;
	//DishTypeAdapter dishTypeAdapter;
	ItemMenuAdapter<DishType> itemMenuAdapter;

	@BindView(R.id.paymentButton)
	FloatingActionButton floatingButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycler_list);
		ButterKnife.bind(this);
		dishTypeViewModel = ViewModelProviders.of(this).get(DishTypeViewModel.class);

		dishTypes = new ArrayList<>();
		itemMenuAdapter = new ItemMenuAdapter<>(dishTypes, new Listener());
		dishTypesListView.setAdapter(itemMenuAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		dishTypesListView.setLayoutManager(linearLayoutManager);
		dishTypesListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		floatingButton.setOnClickListener(this::onClickCartButton);

		loadDishTypes();
	}

	private void onClickCartButton(View v) {
		Intent backToMenu;
		backToMenu = new Intent(DishTypeActivity.this, OrderActivity.class);
		startActivity(backToMenu);
	}

	private class Listener implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			boolean hasSubtypes = dishTypes.get(position).getHasSubtypes();

			if (hasSubtypes) {
				//IR A ACTIVITY CON SUBTIPOS
				Intent showSubtypes;
				showSubtypes = new Intent(DishTypeActivity.this, DishSubtypeActivity.class);
				showSubtypes.putExtra(AppString.CLICKED_ITEM_ID, dishTypes.get(position).getId());
				startActivity(showSubtypes);
			} else {
				//IR A ACTIVITY CON PLATOS
				Intent showDishes;
				showDishes = new Intent(DishTypeActivity.this, DishSubtypeActivity.class);
				showDishes.putExtra(AppString.DISH_TYPE_ID, dishTypes.get(position).getId());
				startActivity(showDishes);
				Toast.makeText(DishTypeActivity.this, "Tipo de plato sin subtipos", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void loadDishTypes() {
		final LiveData<List<DishType>> dishTypesLiveData = dishTypeViewModel.getDishTypesList();
		Observer<List<DishType>> dishTypesResponseObserver = new ObserverDishTypes(dishTypesLiveData);
		dishTypesLiveData.removeObservers(this);
		dishTypesLiveData.observe(DishTypeActivity.this, dishTypesResponseObserver);
	}

	private void updateDishTypesOnUI(List<DishType> dishTypes) {
		this.dishTypes.clear();
		this.dishTypes.addAll(dishTypes);
		itemMenuAdapter.notifyDataSetChanged();
		Toast.makeText(DishTypeActivity.this, "DishTypes obtenidos", Toast.LENGTH_LONG).show();
	}

	private class ObserverDishTypes implements Observer<List<DishType>> {
		private LiveData<List<DishType>> dishTypesLiveData;

		ObserverDishTypes(LiveData<List<DishType>> dishTypesLiveData) {
			this.dishTypesLiveData = dishTypesLiveData;
		}

		@Override
		public void onChanged(@Nullable List<DishType> dishTypes) {
			if (dishTypes != null) {
				updateDishTypesOnUI(dishTypes);
				dishTypesLiveData.removeObserver(this);
			}
		}
	}
}