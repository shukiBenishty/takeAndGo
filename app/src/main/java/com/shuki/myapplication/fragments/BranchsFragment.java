package com.shuki.myapplication.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shuki.myapplication.R;
import com.shuki.myapplication.controller.MainActivity;
import com.shuki.myapplication.entities.Branch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class BranchsFragment extends Fragment {

    static Activity activity;
    public static ContentAdapter adapter;
    public static Context context;

    @SuppressLint("ValidFragment")
    public BranchsFragment(ArrayList<Branch> branchs) {
        adapter = new ContentAdapter(branchs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        context = getContext();

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

     public static void replaceFragment(int position){
        CarsFragment carsFragment =  (CarsFragment) MainActivity.adapter.mFragmentList.get(2);
        carsFragment.branchSpiner.setSelection(position + 1);
        MainActivity.adapter.notifyDataSetChanged();
        MainActivity.tabs.getTabAt(2).select();
    }

    public static class BranchHolder extends RecyclerView.ViewHolder {

        public ImageView branchPicture;
        public TextView branchName;
        public TextView branchAddress;
        public Button carLiatButton;
        public ImageButton locationButton;
        public NumberPicker parkingNum;

        public BranchHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.branch_item, parent, false));

            branchPicture = (ImageView) itemView.findViewById(R.id.card_image);
            branchName = (TextView) itemView.findViewById(R.id.card_title);
            branchAddress = (TextView) itemView.findViewById(R.id.card_text);
            parkingNum = (NumberPicker) itemView.findViewById(R.id.parking_num) ;
            carLiatButton = (Button)itemView.findViewById(R.id.action_button);
            locationButton = (ImageButton) itemView.findViewById(R.id.share_button);
        }
    }


    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<BranchHolder> {
        public static ArrayList<Branch> branchList;


        public ContentAdapter(ArrayList<Branch> branchs) {
            branchList = branchs;
        }

        @Override
        public BranchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BranchHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(BranchHolder holder, final int position) {
            try {
                final BranchHolder _holder = holder;
                final Branch branch = branchList.get(position);

                holder.parkingNum.setMaxValue(branch.getNumParkingSpaces());
                holder.parkingNum.setMinValue(branch.getNumParkingSpaces());
                holder.parkingNum.setValue(branch.getNumParkingSpaces());
                Picasso.get().load(branch.getBranchImgUrl())
                        .placeholder(R.drawable.default_branch)
                        .error(R.drawable.default_branch)
                        .into(holder.branchPicture);

                holder.branchName.setText(branch.getBranchName());
                holder.branchAddress.setText(branch.getAddress());
                holder.carLiatButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        replaceFragment(position);
                        Snackbar.make(v, "Action is pressed",
                                Snackbar.LENGTH_LONG).show();

                    }
                });
                holder.locationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(context, activity, v, _holder);

                    }
                });
            }
            catch (Exception ex){
                Log.d("onBindViewHolder",ex.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return branchList.size();
        }


        public void showDialog(final Context context, Activity activity, final View view, final BranchHolder holder){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_map);
            MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
            MapsInitializer.initialize(activity);
            mMapView.onCreate(dialog.onSaveInstanceState());
            mMapView.onResume();

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    Geocoder dec = new Geocoder(context);
                    List<Address> address = null;
                    try {
                        address = dec.getFromLocationName(String.valueOf(holder.branchAddress.getText()), 1);
                        if ( address == null  )
                            throw new Exception();
                        Address add = address.get(0);
                        LatLng posisition = new LatLng(add.getLatitude(),add.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(posisition).title(String.valueOf(holder.branchName.getText())));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisition));
                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(view , "location not found",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            dialog.show();

        }
    }
}

