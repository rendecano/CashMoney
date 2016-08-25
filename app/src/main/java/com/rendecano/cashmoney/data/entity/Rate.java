package com.rendecano.cashmoney.data.entity;

/**
 * Created by Ren Decano.
 */

public class Rate {

    private String currency;
    private double conversion;

    public double getConversion() {
        return conversion;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
