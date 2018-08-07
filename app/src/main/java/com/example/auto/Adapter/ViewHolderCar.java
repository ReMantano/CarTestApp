package com.example.auto.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.auto.R;




public class ViewHolderCar extends RecyclerView.ViewHolder {

    EditText owner,brand,name,type,type_class,price,date,engine,speed,fuel,transmisson;
    ImageView image;
    ImageButton imageButton;

    public ViewHolderCar(View v) {
        super(v);


        owner =  v.findViewById(R.id.editDetalOwner);
        brand =  v.findViewById(R.id.editDetalBrand);
        name =  v.findViewById(R.id.editDetalName);
        type_class =  v.findViewById(R.id.editCargoClass);
        type =  v.findViewById(R.id.editCargoType);
        transmisson =  v.findViewById(R.id.editTransmissionType);

        price = v.findViewById(R.id.editDetalPrice);
        engine = v.findViewById(R.id.editEnginePower);
        speed =  v.findViewById(R.id.editEngineSpeed);
        fuel = v.findViewById(R.id.editEngineFuel);
        date = v.findViewById(R.id.editDetalDate);

        image =  v.findViewById(R.id.detalImage);

        imageButton = v.findViewById(R.id.headerButton);


    }
}
