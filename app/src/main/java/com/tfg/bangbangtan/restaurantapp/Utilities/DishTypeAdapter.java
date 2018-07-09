package com.tfg.bangbangtan.restaurantapp.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tfg.bangbangtan.restaurantapp.Models.DishType;
import com.tfg.bangbangtan.restaurantapp.R;

import java.util.List;

public class DishTypeAdapter extends BaseAdapter {
	private Context context;
	private int layout;
	private List<DishType> dishTypes;

	public DishTypeAdapter(Context context, int layout, List<DishType> dishTypes) {
		this.context = context;
		this.layout = layout;
		this.dishTypes = dishTypes;
	}

	@Override
	public int getCount() {
		return this.dishTypes.size();
	}

	@Override
	public Object getItem(int position) {
		return dishTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return dishTypes.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(this.context);
			convertView = layoutInflater.inflate(this.layout, null);
			holder = new ViewHolder();
			holder.dishTypeText = convertView.findViewById(R.id.item_text);
			holder.dishTypeIm = convertView.findViewById(R.id.item_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String name = dishTypes.get(position).getName();
		String imageUrl = dishTypes.get(position).getImage();
		Picasso.get().load(imageUrl).into(holder.dishTypeIm);
		holder.dishTypeText.setText(name);
		return convertView;
	}

	private static class ViewHolder {
		private TextView dishTypeText;
		private ImageView dishTypeIm;
	}
}