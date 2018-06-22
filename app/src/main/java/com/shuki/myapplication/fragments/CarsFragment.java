package com.shuki.myapplication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.shuki.myapplication.R;
import com.shuki.myapplication.entities.Branch;
import com.shuki.myapplication.entities.Car;
import com.shuki.myapplication.entities.CarModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CarsFragment extends Fragment {
    public static CarsAdapter adapter;
    public LinearLayout layout;
    public Spinner branchSpiner;

    @SuppressLint("ValidFragment")
    public CarsFragment(ArrayList<Car> carList, ArrayList<CarModel> carModelList) {
        adapter = new CarsAdapter(carList, carModelList);
    }

    public CarsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.recycler_view_spinner, container, false);
//
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.my_recycler_view2);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        branchSpiner= (Spinner) layout.findViewById(R.id.spinner);
//        branchSpiner.setPrompt("Select one option");
        ArrayAdapter<String> spinnerAdapter =  new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                getContext().getResources().getStringArray(R.array.places));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        branchSpiner.setAdapter(spinnerAdapter);
        branchSpiner.setSelection(-1);
        branchSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String branch = (String) adapterView.getAdapter().getItem(i);
//                if(branch.equals("All")){
//                    changeFragement(new openCarFragment());
//                    return;}
//                openCarsByBranchFragment fragment = new openCarsByBranchFragment();
//                Bundle args = new Bundle();
//                args.putString("branch", branch);
//                fragment.setArguments(args);
//                changeFragement(fragment);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0){}
        });
        return layout;
    }


    public static class CarHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public CarHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.car_item, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class CarsAdapter extends RecyclerView.Adapter<CarHolder> {

        public static ArrayList<CarModel> carModelList;
        public static ArrayList<Car> carList;
        public ArrayList<Branch> branchList;


        public CarsAdapter(ArrayList<Car> cars, ArrayList<CarModel> models) {
            carList = cars;
            carModelList = models;
        }

        @Override
        public CarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CarHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(CarHolder holder, int position) {
            try {
                final Car car = carList.get(position);

                CarModel carModel = null;
                for(CarModel c : carModelList){
                    if(c.getCodeModel() == car.getModelID()) {
                        carModel = c;
                        break;
                    }
                }

                Picasso.get().load(carModel.getImgUrl()).placeholder(R.drawable.car).error(R.drawable.car).into(holder.picture);
                holder.name.setText(carModel.getModelName());
            }
            catch (Exception ex){
            Log.d("onBindViewHolder",ex.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return carList.size();
        }
    }


}

