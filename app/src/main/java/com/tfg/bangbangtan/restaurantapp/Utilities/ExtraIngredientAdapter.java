package com.tfg.bangbangtan.restaurantapp.Utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tfg.bangbangtan.restaurantapp.Models.ExtraIngredient;
import com.tfg.bangbangtan.restaurantapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExtraIngredientAdapter extends RecyclerView.Adapter<ExtraIngredientAdapter.ViewHolder> {

    private OnSelectQuantityListener listener;
    private List<ExtraIngredient> extras;

    public interface OnSelectQuantityListener{
        void onSelectQuantity(int position);
    }

   public ExtraIngredientAdapter(List<ExtraIngredient> extras, OnSelectQuantityListener listener){

    this.extras=extras;
    this.listener=listener;

   }

    @NonNull
    @Override
    public ExtraIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_extra_ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraIngredientAdapter.ViewHolder holder, int position) {
        holder.bind(extras.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return extras.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.extra_name_text)
        TextView name_txt;
        @BindView(R.id.extra_price_text)
        TextView price_txt;
        @BindView(R.id.extra_amount_spinner)
        Spinner amount_spinner;
        ArrayAdapter<CharSequence> spinnerAdapter;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            spinnerAdapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.extra_amounts, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            amount_spinner.setAdapter(spinnerAdapter);
        }

        void bind(final ExtraIngredient extraIngredient, final OnSelectQuantityListener listener) {

            this.name_txt.setText(extraIngredient.getName());
            this.price_txt.setText(String.format("%s €", extraIngredient.getPrice()));
            this.amount_spinner.setSelection(extraIngredient.getQuantity()); // la pos cero es tambien cantidad 0 en el array @string

            // Añadimos el listener click para cada elemento spinner
            this.amount_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    extras.get(getAdapterPosition()).setQuantity(position);// EN ESTE CASO POSITION = ID NUM DEL 0-9
                    listener.onSelectQuantity(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
}