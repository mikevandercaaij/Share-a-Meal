package com.mvandercaaij.shareameal.domain;

import java.io.Serializable;
import java.util.ArrayList;

//meal with all values that are available in the api
public abstract class Person implements Serializable {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String city;
    protected String street;
    protected String phoneNumber;
    protected boolean isActive;
    protected ArrayList<String> roles;

    public Person(int id, String firstName, String city, String street, String lastName, String email, String phoneNumber, ArrayList<String> roles, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getFullAddress() {
        return String.format("%s, %s", this.street, this.city);
    }

}