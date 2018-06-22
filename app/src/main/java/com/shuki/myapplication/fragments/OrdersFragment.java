package com.shuki.myapplication.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.shuki.myapplication.entities.Branch;
import com.shuki.myapplication.entities.Car;
import com.shuki.myapplication.entities.CarModel;
import com.shuki.myapplication.entities.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Provides UI for the view with List.
 */
public class OrdersFragment extends Fragment {

    public static ContentAdapter adapter;
    static Activity activity ;

    @SuppressLint("ValidFragment")
    public OrdersFragment(ArrayList<Order> orders, ArrayList<Branch> branchs, ArrayList<Car> cars, ArrayList<CarModel> models) {
        adapter = new ContentAdapter(orders, branchs, cars, models);
    }
    public OrdersFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

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
        public static ArrayList<Order> orderList = new ArrayList<Order>();
        public static ArrayList<Branch> branchList = new ArrayList<Branch>();
        public static ArrayList<Car> carList = new ArrayList<Car>();
        public static ArrayList<CarModel> carModelList = new ArrayList<CarModel>();

        public ContentAdapter(ArrayList<Order> orders, ArrayList<Branch> branchs, ArrayList<Car> cars, ArrayList<CarModel> models) {
            carList = cars;
            branchList = branchs;
            orderList = orders;
            carModelList = models;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {
                final ViewHolder _holder = holder;
                final Order order = orderList.get(position);
                Car car = null;
                CarModel carModel = null;
                Branch branch = null;
                for(Car c : carList){
                    if(c.getId().equals(order.getCarID()) ){
                        car = c;
                        break;
                    }
                }
                for(CarModel c : carModelList){
                    if(c.getCodeModel() == car.getModelID()) {
                        carModel = c;
                        break;
                    }
                }
                for(Branch b : branchList){
                    if(b.getId() == car.getBranchID()) {
                        branch = b;
                        break;
                    }
                }

                Picasso.get().load(carModel.getImgUrl())
                        .placeholder(R.drawable.car)
                        .error(R.drawable.car)
                        .into(holder.carAvator);

                holder.carName.setText(carModel.getModelName());
                holder.branchAddress.setText(branch.getAddress());

                final Branch finalBranch = branch;
                holder.order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v
                    ) {
                        new ViewDialog(_holder).showDialog(activity, finalBranch.getBranchImgUrl());
                    }
                });
            }
            catch (Exception ex){
                Log.d("onBindViewHolder",ex.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return orderList.size();
        }

        public static class ViewDialog {
            ViewHolder _holder;
            public ViewDialog(ViewHolder holder) {
                _holder = holder;
            }

            public void showDialog(Activity activity, String branchUrl){
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.order_dialog);

                ImageView carPic = (ImageView) dialog.findViewById(R.id.dialog_car_img);
                CircleImageView branchPic = (CircleImageView) dialog.findViewById(R.id.dialog_branch_img);
                EditText carNum = (EditText) dialog.findViewById(R.id.dialog_car_num);
                EditText hoursNum = (EditText) dialog.findViewById(R.id.dialog_hours);
                EditText kmNum = (EditText) dialog.findViewById(R.id.dialog_KM);

                carPic.setImageBitmap(((BitmapDrawable)_holder.carAvator.getDrawable()).getBitmap());
                carNum.setText(_holder.carName.getText());
                Picasso.get().load(branchUrl).placeholder(R.drawable.default_branch).error(R.drawable.default_branch).into(branchPic);
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
