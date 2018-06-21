package com.shuki.myapplication.entities;



public class Customer {
    protected String lastName ;
    protected String firstName ;
    protected int id;
    protected String phoneNumber ;
    protected String email ;
    protected  String creditCard;

    public Customer(String lastName, String firstName, int id, String phoneNumber, String email, String creditCard) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.creditCard = creditCard;
    }

    public Customer(String lastName, String firstName, int id, String phoneNumber, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public Customer() {

    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCard() {
        return this.creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }





}
