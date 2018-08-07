package com.example.auto.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.auto.MainActivity;
import com.example.auto.R;
import com.example.auto.Adapter.RecyclerViewCarAdapter;

import static com.example.auto.MainActivity.FRAGMENT_CREATE_CAR_TAG;
import static com.example.auto.MainActivity.FRAGMENT_EDIT_CAR_TAG;


public class CarListFragment extends Fragment implements View.OnClickListener{

    RecyclerViewCarAdapter adapter;
    RecyclerView recycler;
    ImageView imagePriceArrow;

    public final String whereOwner = "WHERE owner.owner_name = '";
    public final String whereBrand = "brand.brand_name = '";
    public final String order = "ORDER BY car.price ";

    public String queryWhereOwner = "";
    public String queryWhereBrand = "";
    public String queryOrder = "" ;
    private boolean key = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity,container,false);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        adapter = new RecyclerViewCarAdapter(((MainActivity) getActivity()).list,this);

        recycler = getActivity().findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        recycler.setLayoutManager(llm);

        recycler.setAdapter(adapter);

        iniSortMenu();

        getActivity().findViewById(R.id.imageView).setOnClickListener((c) ->{

            Fragment fragment = new CarAddFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,
                    android.R.anim.fade_out,android.R.anim.fade_in);

            transaction.addToBackStack(FRAGMENT_CREATE_CAR_TAG);

            transaction.replace(R.id.containerFragment,fragment,FRAGMENT_CREATE_CAR_TAG);

            transaction.commit();
        });

    }

    @Override
    public void onClick(View view) {

        ((MainActivity) getActivity()).currentID = Integer.valueOf(view.getContentDescription().toString());
        Fragment fragment = new CarEditFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,
                                        android.R.anim.fade_out,android.R.anim.fade_in);

        transaction.addToBackStack(FRAGMENT_EDIT_CAR_TAG);

        transaction.replace(R.id.containerFragment,fragment,FRAGMENT_EDIT_CAR_TAG);

        transaction.commit();
    }

    private void iniSortMenu(){
        LinearLayout bt1 = getActivity().findViewById(R.id.buttonPrice);

        imagePriceArrow = getActivity().findViewById(R.id.imagePriceArrow);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (key) {
                    queryOrder = order + "DESC";
                    imagePriceArrow.setImageResource(android.R.drawable.arrow_up_float);
                }
                else {
                    queryOrder = order + "ASC";
                    imagePriceArrow.setImageResource(android.R.drawable.arrow_down_float);
                }
                ((MainActivity) getActivity() ).sortPrice(queryWhereOwner,queryWhereBrand,queryOrder);
                key = !key;

                adapter.notifyDataSetChanged();
            }
        });

        Spinner sp1 = getActivity().findViewById(R.id.spinnerOwner);
        Spinner sp2 = getActivity().findViewById(R.id.spinnerBrand);

        ArrayAdapter<String> a1 = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item,((MainActivity) getActivity() ).getOwners());
        ArrayAdapter<String> a2 = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item,((MainActivity) getActivity() ).getBrands());

        sp1.setAdapter(a1);
        sp2.setAdapter(a2);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getSelectedItem().toString();
                if (item.equals("Owner")){
                    queryWhereOwner = "";
                    ((MainActivity) getActivity() ).sortPrice(queryWhereOwner,queryWhereBrand,queryOrder);
                    adapter.notifyDataSetChanged();
                }else{
                    queryWhereOwner = whereOwner + item + "' ";
                    ((MainActivity) getActivity() ).sortPrice(queryWhereOwner,queryWhereBrand,queryOrder);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getSelectedItem().toString();
                if (item.equals("Brand")){
                    queryWhereBrand = "";
                    ((MainActivity) getActivity() ).sortPrice(queryWhereOwner,queryWhereBrand,queryOrder);
                    adapter.notifyDataSetChanged();
                }else{
                    queryWhereBrand = whereBrand + item + "' ";
                    ((MainActivity) getActivity() ).sortPrice(queryWhereOwner,queryWhereBrand,queryOrder);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
