package com.tfg.bangbangtan.restaurantapp.Utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;
import com.tfg.bangbangtan.restaurantapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExtraIngredientPriceAdapter extends RecyclerView.Adapter<ExtraIngredientPriceAdapter.ViewHolder> {

	private List<ExtraIngredient> extraIngredients;

	ExtraIngredientPriceAdapter(List<ExtraIngredient> extraIngredients) {
		this.extraIngredients = extraIngredients;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_ingredient_price_item_list,parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.bind(extraIngredients.get(position));
	}

	@Override
	public int getItemCount() {
		return extraIngredients.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.extra_ingredient_name)
		TextView name;
		@BindView(R.id.extra_ingredient_price)
		TextView price;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		void bind(final ExtraIngredient extraIngredient){
			this.name.setText(String.format("%s x%d", extraIngredient.getName(), extraIngredient.getQuantity()));
			this.price.setText(String.format("%s€", extraIngredient.getPrice()));
		}
	}
}