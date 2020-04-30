package com.dairydailyadmin.Models;

public class MilkBuyModel {
    private String name, weight, fat, snf, rate, amount, shift;

    public MilkBuyModel() {
    }

    public MilkBuyModel(String name, String weight, String fat, String snf, String rate, String amount, String shift) {
        this.name = name;
        this.weight = weight;
        this.fat = fat;
        this.snf = snf;
        this.rate = rate;
        this.amount = amount;
        this.shift = shift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSnf() {
        return snf;
    }

    public void setSnf(String snf) {
        this.snf = snf;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
