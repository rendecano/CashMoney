package com.rendecano.cashmoney.data.repository;

import android.text.TextUtils;

import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.data.entity.mapper.BaseEntityJsonMapper;
import com.rendecano.cashmoney.data.local.SharedPrefStorage;
import com.rendecano.cashmoney.data.network.JsonResponseListener;
import com.rendecano.cashmoney.data.network.RestApi;
import com.rendecano.cashmoney.data.network.RestApiImpl;
import com.rendecano.cashmoney.domain.ConvertRepository;
import com.rendecano.cashmoney.domain.subscriber.UseCaseSubscriber;
import com.rendecano.cashmoney.presentation.AndroidApplication;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Ren Decano.
 */

public class ConvertDataRepository implements ConvertRepository {

    private RestApi restApi;
    private SharedPrefStorage sharedPrefStorage;
    private final String BASE_CURRENCY = "AUD";
    private final String[] CURRENCIES = {"CAD", "EUR", "GBP", "JPY", "USD"};

    public ConvertDataRepository() {
        restApi = new RestApiImpl(AndroidApplication.getApplicationInstance());
        sharedPrefStorage = new SharedPrefStorage(AndroidApplication.getApplicationInstance());
    }

    @Override
    public void initialValues(final UseCaseSubscriber<Base> subscriber) {

        // Get values from network
        restApi.getExchangeRate(BASE_CURRENCY, CURRENCIES, new JsonResponseListener() {

            @Override
            public void onSuccess(String response) {

                // Create json to object mapper
                BaseEntityJsonMapper baseEntityJsonMapper = new BaseEntityJsonMapper();

                try {
                    // Convert to Base object
                    Base base = baseEntityJsonMapper.transformBaseEntity(response);

                    // Save details to Shared preferences for conversion usage
                    sharedPrefStorage.saveCurrencyConversions(base);

                    subscriber.onNext(base);

                } catch (JSONException e) {
                    subscriber.onError("JSON Exception");
                }
            }

            @Override
            public void onError(String message) {
                subscriber.onError(message);
            }
        });
    }

    @Override
    public void getConvertedCurrency(String pValue, String symbol, UseCaseSubscriber<String> subscriber) {

        // Get value from shared preferences
        double value = sharedPrefStorage.getConversion(symbol);
        double inputValue = Double.valueOf(TextUtils.isEmpty(pValue.replace("$", "").replace(",", "")) ? "0" : pValue.replace("$", "").replace(",", ""));

        DecimalFormat formatter = new DecimalFormat("#,###,###.##");
        String formattedValue = formatter.format(value * inputValue);

        Currency cu = Currency.getInstance(getLocale(symbol));
        subscriber.onNext(cu.getSymbol() + formattedValue);
    }

    private Locale getLocale(String symbol) {
        switch (symbol) {
            case "CAD":
                return Locale.CANADA;
            case "EUR":
                return Locale.GERMANY;
            case "GBP":
                return Locale.UK;
            case "JPY":
                return Locale.JAPAN;
            case "USD":
                return Locale.US;
            default:
                return Locale.getDefault();

        }
    }
}
