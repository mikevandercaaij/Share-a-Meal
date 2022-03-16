package com.mvandercaaij.shareameal.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Cook extends Person implements Serializable {
    //make cook object using its super class person, able to add on to cook later on if needed
    public Cook(int id, String firstName, String city, String street, String lastName, String email, String phoneNumber, ArrayList<String> roles, boolean isActive) {
        super(id,firstName,city, street, lastName, email, phoneNumber, roles, isActive);
    }

}