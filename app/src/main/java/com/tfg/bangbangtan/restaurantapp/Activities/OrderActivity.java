package com.tfg.bangbangtan.restaurantapp.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tfg.bangbangtan.restaurantapp.Models.CustomDish;
import com.tfg.bangbangtan.restaurantapp.Models.Order;
import com.tfg.bangbangtan.restaurantapp.R;
import com.tfg.bangbangtan.restaurantapp.Utilities.CustomDishAdapter;
import com.tfg.bangbangtan.restaurantapp.ViewModels.OrderViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

	OrderViewModel orderViewModel;

	@BindView(R.id.order_cost_txt)
	TextView order_cost_txt;
	@BindView(R.id.send_order_button)
	Button send_order_button;

	@BindView(R.id.OrderIdTestText)
	TextView textID;

	@BindView(R.id.list_custom_dish)
	RecyclerView listCustomDish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		ButterKnife.bind(this);
		orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
		order_cost_txt.setText(String.valueOf(Order.getInstance().getCost()));
		send_order_button.setOnClickListener(this::onClickSendOrder);

		CustomDishAdapter adapter = new CustomDishAdapter(Order.getInstance().getCustomDishes(),this);
		listCustomDish.setAdapter(adapter);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		this.listCustomDish.setLayoutManager(linearLayoutManager);
	}

	private void createOrder() {
		final LiveData<Order> orderLiveData = orderViewModel.createOrder();
		Observer<Order> orderResponseObserver = new ObserverOrder(orderLiveData);
		orderLiveData.removeObservers(this);
		orderLiveData.observe(OrderActivity.this, orderResponseObserver);
	}

	private void createOrderCustomDishes() {
		List<CustomDish> customDishes = Order.getInstance().getCustomDishes();
		for (int i = 0; i < customDishes.size(); i++) {
			CustomDish customDish = customDishes.get(i);
			customDish.setOrderId(Order.getInstance().getId());
			final LiveData<CustomDish> customDishLiveData = orderViewModel.createCustomDish(customDish);
			Observer<CustomDish> customDishResponseObserver = new ObserverCustomDish(customDishLiveData, i);
			customDishLiveData.removeObservers(this);
			customDishLiveData.observe(OrderActivity.this, customDishResponseObserver);
		}
	}

	private void updateOrder(Order order) {
		Order.getInstance().setId(order.getId());
		Toast.makeText(OrderActivity.this, "Creado Order de id: " + Order.getInstance().getId(), Toast.LENGTH_LONG).show();
		//prueba
		textID.setText(String.valueOf(Order.getInstance().getId()));
		//fin prueba
		createOrderCustomDishes();

	}

	private void onClickSendOrder(View v) {
		if(Order.getInstance().getCustomDishes().size() > 0){
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_input_add)
					.setTitle("Confirmar pedido")
					.setMessage("Se enviara un pedido a cocina por un importe de: \n" + Order.getInstance().getCost() + "€\nUna vez enviado no se podra modificar.")
					.setPositiveButton("Si", (dialog, which) -> {
						send_order_button.setVisibility(View.INVISIBLE);
						createOrder();
					})
					.setNegativeButton("No", null)
					.show();

		}else{
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle("Pedido invalido")
					.setMessage("Añade platos a tu pedido")
					.setNeutralButton("Ok", null)
					.show();
		}
	}

	@Override
	public void onClick(View v) {
		order_cost_txt.setText(String.valueOf(Order.getInstance().getCost()));
	}

	private class ObserverOrder implements Observer<Order> {
		private LiveData<Order> orderLiveData;

		ObserverOrder(LiveData<Order> orderLiveData) {
			this.orderLiveData = orderLiveData;
		}

		@Override
		public void onChanged(@Nullable Order order) {
			if (order != null) {
					updateOrder(order);
					orderLiveData.removeObserver(this);
			}
		}
	}

	private class ObserverCustomDish implements Observer<CustomDish> {
		private LiveData<CustomDish> customDishLiveData;
		private int position;

		ObserverCustomDish(LiveData<CustomDish> customDishLiveData,int position) {
			this.customDishLiveData = customDishLiveData;
			this.position = position;
		}

		@Override
		public void onChanged(@Nullable CustomDish customDish) {
			if (customDish != null) {
				customDishLiveData.removeObserver(this);
				Order.getInstance().getCustomDishes().get(position).setId(customDish.getId());
				Toast.makeText(OrderActivity.this, "Creado CustomDish de id: " + customDish.getId(), Toast.LENGTH_LONG).show();
			}
		}
	}

}
