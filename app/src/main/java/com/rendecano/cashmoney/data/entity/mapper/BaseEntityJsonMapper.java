package com.rendecano.cashmoney.data.entity.mapper;

import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.data.entity.Rate;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseEntityJsonMapper {

    public Base transformBaseEntity(String jsonResponse) throws JSONException {

        Base entity = new Base();

        JSONObject jsonObject = new JSONObject(jsonResponse);

        String base = jsonObject.optString("base");
        String date = jsonObject.optString("date");

        entity.setBase(base);
        entity.setDate(date);

        // Get rates
        JSONObject rates = jsonObject.getJSONObject("rates");

        Rate rateCad = new Rate();
        rateCad.setCurrency("CAD");
        rateCad.setConversion(rates.optDouble("CAD"));

        Rate rateGbp = new Rate();
        rateGbp.setCurrency("GBP");
        rateGbp.setConversion(rates.optDouble("GBP"));

        Rate rateJpy = new Rate();
        rateJpy.setCurrency("JPY");
        rateJpy.setConversion(rates.optDouble("JPY"));

        Rate rateUsd = new Rate();
        rateUsd.setCurrency("USD");
        rateUsd.setConversion(rates.optDouble("USD"));

        Rate rateEur = new Rate();
        rateEur.setCurrency("EUR");
        rateEur.setConversion(rates.optDouble("EUR"));

        entity.getRates().add(rateCad);
        entity.getRates().add(rateGbp);
        entity.getRates().add(rateJpy);
        entity.getRates().add(rateUsd);
        entity.getRates().add(rateEur);

        return entity;
    }
}
