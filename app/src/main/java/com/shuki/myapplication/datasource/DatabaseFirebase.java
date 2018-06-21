package com.shuki.myapplication.datasource;

import android.util.Log;

import com.shuki.myapplication.entities.Branch;
import com.shuki.myapplication.entities.Car;
import com.shuki.myapplication.entities.CarModel;
import com.shuki.myapplication.entities.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shuki.myapplication.backend.DataSource;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class DatabaseFirebase implements DataSource {

    FirebaseDatabase database ;
    DatabaseReference customersRef ;
    DatabaseReference carsRef ;
    DatabaseReference carModelsRef ;
    DatabaseReference brachsRef ;



    private ArrayList<Branch> branches ;
    private ArrayList<Car> cars ;
    private ArrayList<CarModel> carModels ;
    private ArrayList<Customer> customers ;

    public DatabaseFirebase() {
        this.database = FirebaseDatabase.getInstance();

        this.customersRef = database.getReference("Customers");
        ValueEventListener customersListner = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (customers == null){
                    customers = new ArrayList<Customer>();
                }
                for (DataSnapshot customerSnapshot: dataSnapshot.getChildren()) {
                    Customer customer = customerSnapshot.getValue(Customer.class);
                    customers.add(customer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        customersRef.addValueEventListener(customersListner);

        this.brachsRef = database.getReference("Branchs");
        ValueEventListener branchsListner = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (branches == null){
                    branches = new ArrayList<Branch>();
                }
                for (DataSnapshot brachSnapshot: dataSnapshot.getChildren()) {
                    Branch branch = brachSnapshot.getValue(Branch.class);
                    branches.add(branch);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        brachsRef.addValueEventListener(branchsListner);

        this.carsRef = database.getReference("Cars");
        ValueEventListener carsListner = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (cars == null){
                    cars = new ArrayList<Car>();
                }
                for (DataSnapshot carSnapshot: dataSnapshot.getChildren()) {
                    Car car = carSnapshot.getValue(Car.class);
                    cars.add(car);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        carsRef.addValueEventListener(carsListner);

        this.carModelsRef = database.getReference("CarModels");
        ValueEventListener carModelsListner = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (carModels == null){
                    carModels = new ArrayList<CarModel>();
                }
                for (DataSnapshot carModelSnapshot: dataSnapshot.getChildren()) {
                    CarModel carModel = carModelSnapshot.getValue(CarModel.class);
                    carModels.add(carModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        carModelsRef.addValueEventListener(carModelsListner);
    }


    @Override
    public void addCustomer(Customer customer) throws Exception {
        customersRef.push().setValue(customer);
    }
    @Override
    public void addCarModle(CarModel carModel) throws Exception {
       carModelsRef.push().setValue(carModel);
    }
    @Override
    public void addCar(Car car) throws Exception {
        carsRef.push().setValue(car);
    }
    @Override
    public void addBranch(Branch branch) throws Exception {
        brachsRef.push().setValue(branch);
    }

    @Override
    public ArrayList<CarModel> getCarModelList() {
            if (carModels == null){
                carModels = new ArrayList<CarModel>();
            }
           return carModels;
    }
    @Override
    public ArrayList<Customer> getCustomerList() {
        if (customers == null){
            customers = new ArrayList<Customer>();
        }
        return customers;
    }
    @Override
    public ArrayList<Branch> getBranchList() {
            if (branches == null){
                branches = new ArrayList<Branch>();
            }

        return branches;

    }
    @Override
    public ArrayList<Car> getCarList() {
            if (cars == null){
                cars = new ArrayList<Car>();
            }

            return cars;
    };


}
