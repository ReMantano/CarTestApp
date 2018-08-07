package com.example.auto.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.auto.R;

import java.util.ArrayList;



public class RecyclerViewCarAdapter extends RecyclerView.Adapter<ViewHolderCar> {


    private ArrayList<Car> list;
    private View.OnClickListener listener;

    public RecyclerViewCarAdapter(ArrayList<Car> list, View.OnClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolderCar onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car,parent,false);
        ViewHolderCar item = new ViewHolderCar(v);

        return item;
    }

    @Override
    public void onBindViewHolder(ViewHolderCar holder, int position) {

        Car car = list.get(position);

        holder.owner.setText(car.owner);
        holder.brand.setText(car.brand);
        holder.type.setText(car.type);
        holder.type_class.setText(car.class_type);
        holder.transmisson.setText(car.transmission);
        holder.name.setText(car.name);

        holder.price.setText(String.valueOf(car.price));
        holder.engine.setText(String.valueOf(car.engine));
        holder.speed.setText(String.valueOf(car.speed));
        holder.fuel.setText(String.valueOf(car.fuel));
        holder.date.setText(String.valueOf(car.date));
        if(car.bitmap != null) holder.image.setImageBitmap(car.bitmap);

        holder.imageButton.setOnClickListener(listener);

        holder.imageButton.setContentDescription(String.valueOf(holder.getAdapterPosition()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Car> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
