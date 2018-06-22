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
import com.shuki.myapplication.controller.MainActivity;
import com.shuki.myapplication.entities.Branch;
import com.shuki.myapplication.entities.Car;
import com.shuki.myapplication.entities.CarModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CarsFragment extends Fragment {

    public static ArrayList<Branch> branchList = new ArrayList<Branch>();
    public static ArrayList<Car> carList = new ArrayList<Car>();
    public static ArrayList<CarModel> carModelList = new ArrayList<CarModel>();
    public static CarsAdapter adapter;
    public LinearLayout layout;
    public Spinner branchSpiner;
    public static ArrayAdapter<String> spinnerAdapter;

    @SuppressLint("ValidFragment")
    public CarsFragment(ArrayList<Car> carList, ArrayList<CarModel> carModelList) {
        adapter = new CarsAdapter(carList, carModelList);
    }

    public CarsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.recycler_view_spinner, container, false);

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.my_recycler_view2);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        initSpinner();

        return layout;
    }

    public void updateSpinner(){
        if (spinnerAdapter == null ) {
            initSpinner();
        }
        else {
            ArrayList<String> itemArray = new ArrayList<>();
            itemArray.add("All");
            for (Branch b : branchList){
                itemArray.add(b.getBranchName());
            }
            spinnerAdapter.clear();
            spinnerAdapter.addAll(itemArray);
            spinnerAdapter.notifyDataSetChanged();
        }

    }
    private void initSpinner() {
        try {
            branchSpiner = (Spinner) layout.findViewById(R.id.spinner);

            ArrayList<String> itemArray = new ArrayList<>();
            itemArray.add("All");
            for (Branch b : branchList) {
                itemArray.add(b.getBranchName());
            }
            spinnerAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, itemArray);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            branchSpiner.setAdapter(spinnerAdapter);
            branchSpiner.setSelection(-1);
            branchSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    try {
                        String branchName = (String) adapterView.getAdapter().getItem(i);
                        Branch branch = null;
                        for (Branch b : branchList) {
                            if (b.getBranchName().equals(branchName)) {
                                branch = b;
                                break;
                            }
                        }
                        ArrayList<Car> carListTemp = new ArrayList<>();
                        if (branchName.equals("All")) {
                            carListTemp = MainActivity.carList;
                        } else {
                            for (Car c : MainActivity.carList) {
                                if (c.getBranchID() == branch.getId())
                                    carListTemp.add(c);
                            }
                        }


                        CarsAdapter.carList = carListTemp;
                        adapter.notifyDataSetChanged();
                    } catch (Exception ex) {
                        Log.d("", "");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
        }
        catch (Exception ex){
            Log.d("","");
        }
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

