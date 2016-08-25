package com.rendecano.cashmoney.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.data.entity.Rate;
import com.rendecano.cashmoney.presentation.AndroidApplication;

public class SharedPrefStorage {

    public static final String PREFS_NAME = "CASH_MONEY_PREFS";
    SharedPreferences prefs;

    public SharedPrefStorage(Context pContext) {
        prefs = pContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveCurrencyConversions(Base pBase) {

        savePreference("BASE", pBase.getBase());
        savePreference("DATE", pBase.getDate());

        if (pBase.getRates().size() > 0) {
            for (Rate rate : pBase.getRates()) {
                savePreference(rate.getCurrency(), String.valueOf(rate.getConversion()));
            }
        }
    }

    public double getConversion(String pCurrency) {
        String conversion = prefs.getString(pCurrency, "");
        return Double.valueOf(conversion);
    }

    private void savePreference(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
