package com.shuki.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrdersFragment fragment = new OrdersFragment();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
                    dialog.setContentView(R.layout.dialog_map);////your custom content
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
                                address = dec.getFromLocationName("dtrytrydtffytr", 1);
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
        private final Drawable[] mPlacePictures;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_locations);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public BranchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BranchHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(BranchHolder holder, int position) {
            holder.parkingNum.setValue(8);
            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
            holder.name.setText(mPlaces[position % mPlaces.length]);
            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}

