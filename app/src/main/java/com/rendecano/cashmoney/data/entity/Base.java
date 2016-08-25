package com.rendecano.cashmoney.data.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ren Decano.
 */

public class Base {

    private String base;
    private String date;
    private List<Rate> rates = new ArrayList<>();

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
