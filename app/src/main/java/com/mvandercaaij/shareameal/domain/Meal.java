package com.mvandercaaij.shareameal.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//meal with all values that are available in the api
public class Meal implements Serializable {

    private int id;
    private String name;
    private String description;

    private boolean isActive;
    private boolean isVega;
    private boolean isVegan;
    private boolean isToTakeHome;
    private Instant dateTime;
    private Instant createDate;
    private Instant updateDate;
    private int maxAmountOfParticipants;
    private Double price;
    private String imageUrl;
    private ArrayList<String> allergenes;
    private ArrayList<Participant> participants;
    private Cook cook;

    public Meal(int id, String name, String description, boolean isActive, boolean isVega,
                boolean isVegan, boolean isToTakeHome, String dateTime, String createDate,
                String updateDate, int maxAmountOfParticipants, Double price, String imageUrl, ArrayList<String> allergenes,
                Cook cook, ArrayList<Participant> participants) {
        this.name = name;
        this.description = description;
        //temp data bec of no api data
        this.isActive = isActive;
        this.isVega = isVega;
        this.isVegan = isVegan;
        this.isToTakeHome = isToTakeHome;
        this.dateTime = Instant.parse(dateTime);
        this.createDate = Instant.parse(createDate);
        this.updateDate = Instant.parse(updateDate);
        this.maxAmountOfParticipants = maxAmountOfParticipants;
        this.price = price;
        this.imageUrl = imageUrl;
        this.allergenes = allergenes;
        this.participants = participants;
        this.cook = cook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isVega() {
        return isVega;
    }

    public void setVega(boolean vega) {
        isVega = vega;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean isToTakeHome() {
        return isToTakeHome;
    }

    public void setToTakeHome(boolean toTakeHome) {
        isToTakeHome = toTakeHome;
    }

    public String getDateTime() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC"))
                .format(Instant.parse(this.dateTime.toString()));
    }

    public String getCreateDate() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC"))
                .format(Instant.parse(this.createDate.toString()));
    }

    public String getUpdateDate() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC"))
                .format(Instant.parse(this.updateDate.toString()));
    }

    public int getMaxAmountOfParticipants() {
        return maxAmountOfParticipants;
    }

    public void setMaxAmountOfParticipants(int maxAmountOfParticipants) {
        this.maxAmountOfParticipants = maxAmountOfParticipants;
    }

    //return string of all allergen, separated by ", "
    public String getAllergenes() {
        StringBuilder text = new StringBuilder();
        int listSize = this.allergenes.size();

        if(listSize > 0) {
            for(int i = 0; i < listSize; i++) {
                text.append(this.allergenes.get(i));

                if(i != (listSize - 1)) {
                    text.append(", ");
                }
            }
        }
        return text.toString();
    }

    //return string of all participant names, separated by ", "
    public String getParticipantsNames() {
        StringBuilder text = new StringBuilder();
        int listSize = this.participants.size();

        if(listSize > 0) {
            for(int i = 0; i < listSize; i++) {
                text.append(this.participants.get(i).getFirstName());
                text.append(" ");
                text.append(this.participants.get(i).getLastName());

                if(i != (listSize - 1)) {
                    text.append(", ");
                }
            }
        }
        return text.toString();
    }

    public Person getCook() {
        return cook;
    }

    //alter Double to euro format and put "€ " in front of it
    public String getPrice() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("€ ");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

        return nf.format(this.price).trim();
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAmountOfParticipants() {
        return this.participants.size();
    }

    @Override
    public String toString() {
        return this.name;
    }
}