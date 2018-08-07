package com.example.auto.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.auto.Adapter.Car;
import com.example.auto.MainActivity;
import com.example.auto.R;



public class CarEditFragment extends Fragment {

    EditText    editOwner, editBrand,editName, editPrice, editDate, editType, editClass,
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
        makePage();


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

        editPrice.setHint("Price");
        editDate.setHint("Date");
        editType.setHint("Type");
        editClass.setHint("Class");
        editTranssmission.setHint("Transmission");
        editPower.setHint("Power");
        editSpeed.setHint("Speed");
        editFuel.setHint("Fuel");

        imageCar = getActivity().findViewById(R.id.detalImage);

        imageEdit = getActivity().findViewById(R.id.headerButton);

        imageEdit.setImageResource(R.drawable.ic_done);

        imageEdit.setOnClickListener((click) ->{
            if(checkFillField()) {
                saveInstance();
                imageEdit.setVisibility(View.INVISIBLE);
            }
        });

        switchEnabled();

        editPrice.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editOwner, InputMethodManager.SHOW_IMPLICIT);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void makePage(){

        Car car = ((MainActivity) getActivity()).list.get((((MainActivity) getActivity() ).currentID));

        editOwner.setText(car.owner);
        editBrand.setText(car.brand);
        editType.setText(car.type);
        editClass.setText(car.class_type);
        editTranssmission.setText(car.transmission);
        editPrice.setText(String.valueOf(car.price));
        editDate.setText(String.valueOf(car.date));
        editPower.setText(String.valueOf(car.engine));
        editSpeed.setText(String.valueOf(car.speed));
        editFuel.setText(String.valueOf(car.fuel));
        editName.setText(car.name);

        imageCar.setImageBitmap(car.bitmap);
        imageEdit.setContentDescription(String.valueOf(car.id));

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

    private void saveInstance(){

        String class_type = editClass.getText().toString();
        String type = editType.getText().toString();
        String transmisssion = editTranssmission.getText().toString();
        int id = Integer.valueOf(imageEdit.getContentDescription().toString());
        int price = Integer.valueOf(editPrice.getText().toString());
        int date = Integer.valueOf(editDate.getText().toString());
        int power = Integer.valueOf(editPower.getText().toString());
        int speed = Integer.valueOf(editSpeed.getText().toString());
        int fuel = Integer.valueOf(editFuel.getText().toString());

        Car newCar = new Car(
                "","","",type,class_type,transmisssion,id,price,date,power,speed,fuel,null);

        ((MainActivity) getActivity()).saveEditCar(newCar);

        switchEnabled();

    }

    private void switchEnabled(){

        boolean enabled = !editPrice.isEnabled();

        editPrice.setEnabled(enabled);
        editDate.setEnabled(enabled);
        editType.setEnabled(enabled);
        editClass.setEnabled(enabled);
        editTranssmission.setEnabled(enabled);
        editPower.setEnabled(enabled);
        editSpeed.setEnabled(enabled);
        editFuel.setEnabled(enabled);
    }
}
