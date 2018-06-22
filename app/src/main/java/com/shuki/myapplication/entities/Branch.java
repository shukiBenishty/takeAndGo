package com.shuki.myapplication.entities;


import com.shuki.myapplication.backend.BackendFactory;

import java.util.ArrayList;

public class Branch  {

    protected int id;
    protected int numParkingSpaces;
    protected String city;
    protected String street;
    protected int streetNumber;
    protected String branchImgUrl;
    private String branchName;

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }



    public String getBranchName() {
        return branchName;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBranchImgUrl() {
        return branchImgUrl;
    }

    public void setBranchImgUrl(String branchImgUrl) {
        this.branchImgUrl = branchImgUrl;
    }


    public Branch(int id, int numParkingSpaces, String city, String street, int streetNumber) {
        this.id = id;
        this.numParkingSpaces = numParkingSpaces;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public Branch(int numParkingSpaces , String city, String street, int streetNumber) throws Exception {

        ArrayList<Branch> branchArrayList = BackendFactory.getDataSource().getBranchList();
        this.id = 0;
        for (Branch branch : branchArrayList) {
            if ((branch.street + branch.city).equals(street + city))
                throw new Exception("the branch already exsit in the address");
            if (branch.getId() > id){
                this.id = branch.getId();
            }
        }
        this.id++;
        this.numParkingSpaces = numParkingSpaces;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }


    public Branch() {
    }


    public int getId() {
        return id;
    }

    public int getNumParkingSpaces() {
        return numParkingSpaces;
    }

    public void setNumParkingSpaces(int numParkingSpaces) throws Exception {

        if (numParkingSpaces < 0){
            throw new Exception("numParkingSpaces can't < 0");
        }
        this.numParkingSpaces = numParkingSpaces;
    }

    public String getAddress() {
        return this.street + ": " + this.streetNumber + ", " + this.city;
    }


}
