package com.shuki.myapplication.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shuki.myapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Provides UI for the view with List.
 */
public class OrdersFragment extends Fragment {
    static Activity activity ;
    static String[] carsPictures = new String[]{
        "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/designed-for-you_15_41816.png",
                "cdn0.weddingwire.in/emp/fotos/1/8/1/6/charming-places_15_41816.png",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/maruti-suzuki-ciaz-facelift-exterior-87489_15_41816.jpg",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/maruti-suzuki-ciaz-facelift-exterior-87489_15_41816.jpg",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/maruti-suzuki-ciaz-facelift-exterior-87489_15_41816.jpg",
                "http://cdn0.weddingwire.in/emp/fotos/1/8/1/6/download_15_41816.jpg",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout order;
        public ImageView carAvator;
        public TextView carName;
        public TextView branchAddress;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.order_item, parent, false));
            order = (RelativeLayout) itemView.findViewById(R.id.order_item) ;
            carAvator = (ImageView) itemView.findViewById(R.id.list_avatar);
            carName = (TextView) itemView.findViewById(R.id.Order_title);
            branchAddress = (TextView) itemView.findViewById(R.id.list_desc);
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private final String[] mPlaces ;
        private final String[] mPlaceDesc;

        private final String[] carsName = new String[]{
                "Toyota",
                "Honda",
                "Mitsubishi",
                "Kia",
                "Subaru",
                "Suzuki",
        };


        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_locations);
            TypedArray a = resources.obtainTypedArray(R.array.place_avator);

            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ViewHolder _holder = holder;
            Picasso.get().load(carsPictures[position % mPlaces.length])
                            .placeholder(R.drawable.car)
                            .error(R.drawable.car)
                            .into(holder.carAvator);
            holder.carName.setText(carsName[position % mPlaces.length]);
            holder.branchAddress.setText(mPlaceDesc[position % mPlaceDesc.length]);
            holder.order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v
                ) {
                    new ViewDialog(_holder).showDialog(activity,"order_dialog");
                }
            });
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }

        public static class ViewDialog {
            ViewHolder _holder;
            public ViewDialog(ViewHolder holder) {
                _holder = holder;
            }

            public void showDialog(Activity activity, String msg){
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.order_dialog);

                ImageView carPic = (ImageView) dialog.findViewById(R.id.dialog_car_img);
                EditText carNum = (EditText) dialog.findViewById(R.id.dialog_car_num);
                EditText hoursNum = (EditText) dialog.findViewById(R.id.dialog_hours);
                EditText kmNum = (EditText) dialog.findViewById(R.id.dialog_KM);

                carPic.setImageBitmap(((BitmapDrawable)_holder.carAvator.getDrawable()).getBitmap());
                carNum.setText(_holder.carName.getText());
                hoursNum.setText("8");

                Button payButton = (Button) dialog.findViewById(R.id.dialog_pay_button);
                payButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        }
    }
}
