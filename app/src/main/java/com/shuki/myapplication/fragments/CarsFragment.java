package com.shuki.myapplication.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.squareup.picasso.Picasso;


public class CarsFragment extends Fragment {
    public LinearLayout layout;
    public Spinner branchSpiner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.recycler_view_spinner, container, false);
//
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.my_recycler_view2);
//        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        CarsAdapter adapter = new CarsAdapter(recyclerView.getContext());
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
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;

        private final String[] mPlaces;
        private final Drawable[] mDrawablePictures;
        private final String[] mPlacePictures;
        public CarsAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = new String[]{
                    "Toyota",
                    "Honda",
                    "Mitsubishi",
                    "Kia",
                    "Subaru",
                    "Suzuki",
            };

            mPlacePictures = new String[]{
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/designed-for-you_15_41816.png",
                "cdn0.weddingwire.in/emp/fotos/1/8/1/6/charming-places_15_41816.png",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/maruti-suzuki-ciaz-facelift-exterior-87489_15_41816.jpg",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/maruti-suzuki-ciaz-facelift-exterior-87489_15_41816.jpg",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/maruti-suzuki-ciaz-facelift-exterior-87489_15_41816.jpg",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/download_15_41816.jpg",
            };
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mDrawablePictures = new Drawable[a.length()];
            for (int i = 0; i < mDrawablePictures.length; i++) {
                mDrawablePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public CarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CarHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(CarHolder holder, int position) {
            Picasso.get().load(mPlacePictures[position % mPlaces.length]).placeholder(R.drawable.car).error(R.drawable.car).into(holder.picture);
            holder.name.setText(mPlaces[position % mPlaces.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


}

