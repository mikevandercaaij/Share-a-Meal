package com.mvandercaaij.shareameal.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Participant extends Person implements Serializable {
    //make participant object using its super class person, able to add on to participant later on if needed
    public Participant(int id, String firstName, String city, String street, String lastName, String email, String phoneNumber, ArrayList<String> roles, boolean isActive) {
        super(id,firstName,city, street, lastName, email, phoneNumber, roles, isActive);
    }

}