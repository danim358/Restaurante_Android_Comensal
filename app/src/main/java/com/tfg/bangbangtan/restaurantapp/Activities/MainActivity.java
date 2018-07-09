package com.tfg.bangbangtan.restaurantapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tfg.bangbangtan.restaurantapp.API_Management.APIManager;
import com.tfg.bangbangtan.restaurantapp.Models.DishSubtype;
import com.tfg.bangbangtan.restaurantapp.Models.DishType;
import com.tfg.bangbangtan.restaurantapp.R;
import com.tfg.bangbangtan.restaurantapp.Utilities.AppString;
import com.tfg.bangbangtan.restaurantapp.Utilities.DishTypeAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	private ListView dishTlistview;
	private List<DishType> dishTypes;
	private List<DishSubtype> relatedSubtypes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		APIManager.getInstance().getDishTypes(new APIManager.ResponseCallback<List<DishType>>() {
			@Override
			public void OnResponseSuccess(List<DishType> responseObject) {
				dishTypes = responseObject;
				DishTypeAdapter dishTypeAdapter = new DishTypeAdapter(MainActivity.this, R.layout.list_menu_item, dishTypes);
				dishTlistview.setAdapter(dishTypeAdapter);
				Toast.makeText(MainActivity.this, "DishTypes obtenidos", Toast.LENGTH_LONG).show();
			}

			@Override
			public void OnResponseFailure() {
				Toast.makeText(MainActivity.this, "Error al traer DishTypes", Toast.LENGTH_LONG).show();
			}
		});
		dishTlistview = (ListView) findViewById(R.id.mainDishTypeList);
		dishTlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				APIManager.getInstance().getDishSubtypes(dishTypes.get(position).getId(), new APIManager.ResponseCallback<List<DishSubtype>>() {
					@Override
					public void OnResponseSuccess(List<DishSubtype> responseObject) {
						relatedSubtypes = responseObject;
						Toast.makeText(MainActivity.this, "DishSubTypes obtenidos", Toast.LENGTH_LONG).show();
						Intent showTypeContent;
						if (!relatedSubtypes.isEmpty()) { //IR A ACTIVITY CON SUBTIPOS
							showTypeContent = new Intent(MainActivity.this, DishTypeActivity.class);
							//showTypeContent.putExtra(AppString.CLICKED_HAS_SUBTYPE, true);
						} else {

							showTypeContent = new Intent(MainActivity.this, DishTypeActivity.class);
							//showTypeContent.putExtra(AppString.CLICKED_HAS_SUBTYPE, true);

						/*showTypeContent = new Intent(MainActivity.this, DishListActivity.class);
						showTypeContent.putExtra(AppString.CLICKED_HAS_SUBTY, false);
						*/
						}
						showTypeContent.putExtra(AppString.CLICKED_ITEM_ID, dishTypes.get(position).getId());
						startActivity(showTypeContent);
					}

					@Override
					public void OnResponseFailure() {
						Toast.makeText(MainActivity.this, "Error al traer DishSubTypes", Toast.LENGTH_LONG).show();
					}
				});
			}
		});


	}

}
