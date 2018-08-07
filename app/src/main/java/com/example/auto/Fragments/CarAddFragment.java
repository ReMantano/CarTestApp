package com.example.auto.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.auto.Adapter.Car;
import com.example.auto.MainActivity;
import com.example.auto.R;

import java.io.InputStream;

/**
 * Created by Vladislav on 06.08.2018.
 */

public class CarAddFragment extends Fragment {
    private final int SELECT_PICTURE = 1;


    EditText  editName,editOwner, editBrand,editPrice, editDate, editType, editClass,
            editTranssmission, editPower,editSpeed, editFuel;


    ImageView imageEdit, imageCar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_car,container,false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initEditText();
    }

    private void initEditText(){
        editOwner = getActivity().findViewById(R.id.editDetalOwner);
        editBrand = getActivity().findViewById(R.id.editDetalBrand);
        editPrice = getActivity().findViewById(R.id.editDetalPrice);
        editDate = getActivity().findViewById(R.id.editDetalDate);
        editType = getActivity().findViewById(R.id.editCargoType);
        editClass = getActivity().findViewById(R.id.editCargoClass);
        editTranssmission = getActivity().findViewById(R.id.editTransmissionType);
        editPower = getActivity().findViewById(R.id.editEnginePower);
        editSpeed = getActivity().findViewById(R.id.editEngineSpeed);
        editFuel = getActivity().findViewById(R.id.editEngineFuel);
        editName = getActivity().findViewById(R.id.editDetalName);

        editName.setHint("Name");
        editOwner.setHint("Owner");
        editBrand.setHint("Brand");
        editPrice.setHint("Price");
        editDate.setHint("Date");
        editType.setHint("Type");
        editClass.setHint("Class");
        editTranssmission.setHint("Transmission");
        editPower.setHint("Power");
        editSpeed.setHint("Speed");
        editFuel.setHint("Fuel");

        imageCar = getActivity().findViewById(R.id.detalImage);

        imageCar.setOnClickListener((click) ->{
            Intent intent = new Intent();
            intent.setType("image/png");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getActivity().startActivityForResult(Intent.createChooser(intent,"Select image"),SELECT_PICTURE);
        });

        imageEdit = getActivity().findViewById(R.id.headerButton);

        imageEdit.setImageResource(R.drawable.ic_done);

        imageEdit.setOnClickListener((click) ->{
            if(checkFillField()) {
                saveNewCar();
                imageEdit.setVisibility(View.INVISIBLE);
            }
        });

        switchEnabled();

        editName.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editOwner, InputMethodManager.SHOW_IMPLICIT);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private boolean checkFillField() {
        return  check(editBrand) & check(editOwner) & check(editName) & check(editType) &
                check(editClass) & check(editDate) & check(editFuel) & check(editPower) &
                check(editPrice) & check(editTranssmission) & check(editSpeed);
    }

    private boolean check(EditText e){
        if (e.getText().toString().equals("")) {
            e.setHint("Empty field");
            e.setHintTextColor(Color.RED);
            return false;
        }else return true;
    }

    private void saveNewCar(){

        imageCar.invalidate();

        BitmapDrawable bitDraw = (BitmapDrawable) imageCar.getDrawable();

        Bitmap bitmap = bitDraw.getBitmap();

        String owner = editOwner.getText().toString();
        String brand = editBrand.getText().toString();
        String name = editName.getText().toString();
        String class_type = editClass.getText().toString();
        String type = editType.getText().toString();
        String transmisssion = editTranssmission.getText().toString();
        int price = Integer.valueOf(editPrice.getText().toString());
        int date = Integer.valueOf(editDate.getText().toString());
        int power = Integer.valueOf(editPower.getText().toString());
        int speed = Integer.valueOf(editSpeed.getText().toString());
        int fuel = Integer.valueOf(editFuel.getText().toString());

        Car newCar = new Car(
                owner,brand,name,type,class_type,transmisssion,0,price,date,power,speed,fuel,bitmap);

        ((MainActivity) getActivity()).saveNewCar(newCar);

        switchEnabled();


    }

    private void switchEnabled(){

        boolean enabled = !editPrice.isEnabled();

        editName.setEnabled(enabled);
        editOwner.setEnabled(enabled);
        editBrand.setEnabled(enabled);
        editPrice.setEnabled(enabled);
        editDate.setEnabled(enabled);
        editType.setEnabled(enabled);
        editClass.setEnabled(enabled);
        editTranssmission.setEnabled(enabled);
        editPower.setEnabled(enabled);
        editSpeed.setEnabled(enabled);
        editFuel.setEnabled(enabled);
    }

    public InputStream getImagePatch(Uri uri){
        if (uri == null)
            return null;
        InputStream is = null;

        try {
            is = getActivity().getContentResolver().openInputStream(uri);
            Bitmap bm = BitmapFactory.decodeStream(is);
            imageCar.setImageBitmap(bm);

        }catch (Exception e){
            Log.d("Error",e.toString());
        }
        return is;
    }

}
