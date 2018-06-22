package com.shuki.myapplication.backend;


import com.shuki.myapplication.entities.Car;
import com.shuki.myapplication.entities.CarModel;
import com.shuki.myapplication.entities.Customer;
import com.shuki.myapplication.entities.Branch;
import com.shuki.myapplication.entities.Order;

import java.util.ArrayList;

public interface DataSource {

    void addCustomer(Customer customer) throws Exception;
    void addCarModle(CarModel  carModel) throws Exception;
    void addCar(Car car) throws Exception;
    void addBranch(Branch branch) throws Exception;
    void addOrder(Order order) throws Exception;

    ArrayList<CarModel> getCarModelList();
    ArrayList<Customer> getCustomerList();
    ArrayList<Branch> getBranchList();
    ArrayList<Car> getCarList();
    ArrayList<Order> getOrderList();

}
