package com.tfg.bangbangtan.restaurantapp.Utilities;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tfg.bangbangtan.restaurantapp.Models.CustomDish;
import com.tfg.bangbangtan.restaurantapp.Models.Order;
import com.tfg.bangbangtan.restaurantapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDishAdapter extends RecyclerView.Adapter<CustomDishAdapter.ViewHolder> {

	private List<CustomDish> customDishes;
	private View.OnClickListener listener;

	public CustomDishAdapter(List<CustomDish> customDishes, View.OnClickListener listener) {
		this.customDishes = customDishes;
		this.listener=listener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order_custom_dish, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.bind(customDishes.get(position), listener);
	}

	@Override
	public int getItemCount() {
		return customDishes.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.title_custom_dish)
		TextView name;
		@BindView(R.id.price_custom_dish)
		TextView price;
		@BindView(R.id.list_extra_ingredients)
		RecyclerView listExtraIngredients;
		@BindView(R.id.delete_button)
		ImageButton deleteButton;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
			linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
			this.listExtraIngredients.setLayoutManager(linearLayoutManager);
		}

		public void bind(CustomDish customDish, final View.OnClickListener listener){
			name.setText(customDish.getDish().getName());
			price.setText(String.format("%.2f€", customDish.getCost()));
			ExtraIngredientPriceAdapter adapter = new ExtraIngredientPriceAdapter(customDish.getSelectedExtraIngredients());
			this.listExtraIngredients.setAdapter(adapter);

			deleteButton.setOnClickListener(v -> {
				new AlertDialog.Builder(this.itemView.getContext())
						.setIcon(android.R.drawable.ic_delete)
						.setTitle("Quitar plato")
						.setMessage("¿Esta seguro de que desea quitar este plato?")
						.setPositiveButton("Si", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								Order.getInstance().removeCustomDish(getAdapterPosition());
								listener.onClick(null);
								notifyDataSetChanged();
							}
						})
						.setNegativeButton("No", null)
						.show();
			});
		}
	}
}