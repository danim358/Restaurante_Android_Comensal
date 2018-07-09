package com.tfg.bangbangtan.restaurantapp.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tfg.bangbangtan.restaurantapp.Models.Dish;
import com.tfg.bangbangtan.restaurantapp.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.http.Url;

public class DishAdapter extends BaseAdapter {
	private Context context;
	private int layout;
	private List<Dish> dishes;

	public DishAdapter(Context context, int layout, List<Dish> dishes) {

		this.context=context;
		this.layout=layout;
		this.dishes=dishes;
	}

	@Override
	public int getCount() {

		return this.dishes.size();
	}

	@Override
	public Object getItem(int position) {
		return dishes.get(position);
	}

	@Override
	public long getItemId(int position) {

		return dishes.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		ViewHolder holder;
		if(convertView==null) {
			LayoutInflater layoutInflater = LayoutInflater.from(this.context);
			convertView=layoutInflater.inflate(this.layout, null);
			holder= new ViewHolder();

			holder.dishText= (TextView) convertView.findViewById(R.id.txt_Dish);
			holder.dishIm= (ImageView) convertView.findViewById(R.id.img_Dish);
			convertView.setTag(holder); //bind de convert view con los objetos
		}
		else{
			holder= (ViewHolder) convertView.getTag();
		}

		String name= dishes.get(position).getName();
		String image= dishes.get(position).getImage();
		Picasso.get().load(image).into(holder.dishIm);
		holder.dishText.setText(name);
		return convertView;
	}
	static class ViewHolder {
		private TextView dishText;
		private ImageView dishIm;

	}
}
