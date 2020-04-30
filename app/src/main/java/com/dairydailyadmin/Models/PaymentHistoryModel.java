package com.dairydailyadmin.Models;

public class PaymentHistoryModel {
    String date, amount, expiryDate;

    public PaymentHistoryModel() {
    }

    public PaymentHistoryModel(String date, String amount, String expiryDate) {
        this.date = date;
        this.amount = amount;
        this.expiryDate = expiryDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
