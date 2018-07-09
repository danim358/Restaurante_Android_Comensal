package com.tfg.bangbangtan.restaurantapp.Utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tfg.bangbangtan.restaurantapp.Models.MenuItem;
import com.tfg.bangbangtan.restaurantapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemMenuAdapter<T extends MenuItem> extends RecyclerView.Adapter<ItemMenuAdapter.ViewHolder> implements AdapterView.OnItemClickListener {
	private List<T> items;

	private AdapterView.OnItemClickListener onItemClickListener;

	public ItemMenuAdapter(List<T> items, AdapterView.OnItemClickListener onItemClickListener) {
		this.items = items;
		this.onItemClickListener = onItemClickListener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item, parent, false);
		return new ViewHolder<>(view, this);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		String name = items.get(position).getName();
		String image = items.get(position).getImage();
		Picasso.get().load(image).into(holder.imageView);
		holder.textView.setText(name);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (onItemClickListener != null) {
			onItemClickListener.onItemClick(parent, view, position, id);
		}
	}

	static class ViewHolder<T extends MenuItem> extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.item_text)
		TextView textView;
		@BindView(R.id.item_image)
		ImageView imageView;

		ItemMenuAdapter<T> adapter;

		ViewHolder(View itemView, ItemMenuAdapter<T> adapter) {
			super(itemView);
			itemView.setOnClickListener(this);
			this.adapter = adapter;
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void onClick(View v) {
			v.playSoundEffect(SoundEffectConstants.CLICK);
			adapter.onItemClick(null, v, getAdapterPosition(), getItemId());
		}
	}
}