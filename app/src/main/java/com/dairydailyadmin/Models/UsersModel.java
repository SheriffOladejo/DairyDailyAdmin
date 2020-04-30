package com.dairydailyadmin.Models;

import java.util.ArrayList;

public class UsersModel {
    private String name, mobile_no, address, all_buyers, all_sellers, city, email,
            milk_buy_data, milk_sale_data, expiry_date,state,password, date_created;
    private ArrayList<String> subscription_details;

    public UsersModel() {
    }

    public UsersModel(String name, String mobile_no, String address, String all_buyers, String all_sellers, String city, String email, String milk_buy_data, String milk_sale_data, String expiry_date, String state, String password, String date_created, ArrayList<String> subscription_details) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.address = address;
        this.all_buyers = all_buyers;
        this.all_sellers = all_sellers;
        this.city = city;
        this.email = email;
        this.milk_buy_data = milk_buy_data;
        this.milk_sale_data = milk_sale_data;
        this.expiry_date = expiry_date;
        this.state = state;
        this.password = password;
        this.date_created = date_created;
        this.subscription_details = subscription_details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAll_buyers() {
        return all_buyers;
    }

    public void setAll_buyers(String all_buyers) {
        this.all_buyers = all_buyers;
    }

    public String getAll_sellers() {
        return all_sellers;
    }

    public void setAll_sellers(String all_sellers) {
        this.all_sellers = all_sellers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMilk_buy_data() {
        return milk_buy_data;
    }

    public void setMilk_buy_data(String milk_buy_data) {
        this.milk_buy_data = milk_buy_data;
    }

    public String getMilk_sale_data() {
        return milk_sale_data;
    }

    public void setMilk_sale_data(String milk_sale_data) {
        this.milk_sale_data = milk_sale_data;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public ArrayList<String> getSubscription_details() {
        return subscription_details;
    }

    public void setSubscription_details(ArrayList<String> subscription_details) {
        this.subscription_details = subscription_details;
    }
}
