package com.shuki.myapplication.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.squareup.picasso.Picasso;

import java.util.List;


public class BranchsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

     public void replaceFragment(){
//        CarsFragment carsFragment =  (CarsFragment) MainActivity.adapter.mFragmentList.get(2);
//        carsFragment.branchSpiner.setSelection(4);
//        MainActivity.adapter.notifyDataSetChanged();
//        MainActivity.tabs.getTabAt(2).select();
    }

    public  class BranchHolder extends RecyclerView.ViewHolder {

        public ImageView picture;
        public TextView name;
        public TextView description;
        public NumberPicker parkingNum;

        public BranchHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.branch_item, parent, false));

            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            parkingNum = (NumberPicker) itemView.findViewById(R.id.parking_num) ;

            // Adding Snackbar to Action Button inside card
            Button button = (Button)itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    replaceFragment();
                    Snackbar.make(v, "Action is pressed",
                            Snackbar.LENGTH_LONG).show();

                }
            });


            ImageButton locationButton = (ImageButton) itemView.findViewById(R.id.share_button);
            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_map);
                    MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                    MapsInitializer.initialize(getActivity());
                    mMapView.onCreate(dialog.onSaveInstanceState());
                    mMapView.onResume();

                    mMapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(final GoogleMap googleMap) {
                            Geocoder dec = new Geocoder(getContext());
                            List<Address> address = null;
                            try {
                                address = dec.getFromLocationName("8 Rue Scribe, 75009 Paris, France", 1);
                                if ( address == null  )
                                    throw new Exception();
                                Address add = address.get(0);
                                LatLng posisition = new LatLng(add.getLatitude(),add.getLongitude()); ////your lat lng
                                googleMap.addMarker(new MarkerOptions().position(posisition).title("tel aviv"));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisition));
                                googleMap.getUiSettings().setZoomControlsEnabled(true);
                                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Snackbar.make(getView() , "location not found",
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

                    dialog.show();
                }
            });
        }
    }


    /**
     * Adapter to display recycler view.
     */
    public  class ContentAdapter extends RecyclerView.Adapter<BranchHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;

        private final String[] mPlaces;
        private final String[] mPlaceDesc;
        String[] mPlacePictures = new String[]{
                "https://firebasestorage.googleapis.com/v0/b/androaid-app.appspot.com/o/a.png?alt=media&token=e065ec73-fd0b-4153-8a99-b37d8127cac7",
                "https://firebasestorage.googleapis.com/v0/b/androaid-app.appspot.com/o/b.png?alt=media&token=cefc0c0b-8213-4f76-95e7-419f876a9234",
                "https://firebasestorage.googleapis.com/v0/b/androaid-app.appspot.com/o/c.png?alt=media&token=352f76fa-03eb-4a07-9812-68008b981a98",
                "https://firebasestorage.googleapis.com/v0/b/androaid-app.appspot.com/o/d.png?alt=media&token=00cc934f-7e62-4742-a406-64a6703b4153",
                "https://firebasestorage.googleapis.com/v0/b/androaid-app.appspot.com/o/e.png?alt=media&token=c98e7bdb-96d5-4412-bc79-9813b93d4979",
                "https://firebasestorage.googleapis.com/v0/b/androaid-app.appspot.com/o/f.png?alt=media&token=75a90e3c-622f-4972-bea0-b9abc83f5604",
        };


        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_locations);

        }

        @Override
        public BranchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BranchHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(BranchHolder holder, int position) {
            holder.parkingNum.setValue(8);
            Picasso.get().load(mPlacePictures[position % mPlaces.length]).placeholder(R.drawable.default_branch).error(R.drawable.default_branch).into(holder.picture);

            holder.name.setText(mPlaces[position % mPlaces.length]);
            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}

