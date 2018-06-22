//package com.shuki.myapplication.datasource;
//
//
//import com.shuki.myapplication.entities.Branch;
//import com.shuki.myapplication.entities.Car;
//import com.shuki.myapplication.entities.CarModel;
//import com.shuki.myapplication.entities.Customer;
//import com.shuki.myapplication.backend.DataSource;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class DatabaseList implements DataSource {
//
//
//    private String username;
//    private String password;
//    private boolean logon;
//
//    private Map<Integer, Customer> customerMap = new HashMap<>();
//    private Map<Integer , CarModel> carModelMap = new HashMap<>();
//    private Map<Integer , Car> carMap = new HashMap<>();
//    private Map<Integer, Branch> branchMap = new HashMap<>();
//    private Map<String, String> usersPassMap = new HashMap<>();
//
//    public DatabaseList() {
//
//    }
//
////    @Override
//    public Customer isExist(Integer id) {
//
//      return customerMap.get(id);
//    }
//
//    @Override
//    public void addCustomer(Customer customer) throws Exception {
//
//        if(isExist(customer.getId()) != null){
//            throw new  Exception("the customer already exists");
//        }
//        customerMap.put(customer.getId(), customer);
//    }
//
//    @Override
//    public void addCarModle(CarModel carModel) throws Exception {
//        if(carModelMap.get(carModel.getCodeModel()) != null){
//            throw new Exception("the carModle already exists");
//        }
//        carModelMap.put(carModel.getCodeModel(), carModel);
//    }
//
//    @Override
//    public void addCar(Car car) throws Exception{
//        if(carMap.get(car.getId()) != null){
//            throw new Exception("the car already exists");
//        }
//        carMap.put(car.getId(), car);
//    }
//
//    @Override
//    public void addBranch(Branch branch) {
//
//        branchMap.put(branch.getId(), branch);
//
//    }
//
//    @Override
//    public ArrayList<CarModel> getCarModelList() {
//        return new ArrayList<>(carModelMap.values());
//    }
//
//    @Override
//    public ArrayList<Customer> getCustomerList() {
//        return new ArrayList<>(customerMap.values());
//    }
//
//    @Override
//    public ArrayList<Branch> getBranchList() {
//        return new ArrayList<>(branchMap.values());
//    }
//
//    @Override
//    public ArrayList<Car> getCarList() {
//        return new ArrayList<>(carMap.values());
//    }
//
//}
