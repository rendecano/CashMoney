package com.rendecano.cashmoney.data.repository;

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
    public void getConvertedCurrency(double pValue, String symbol, UseCaseSubscriber<String> subscriber) {

        // Get value from shared preferences
        double value = sharedPrefStorage.getConversion(symbol);

        // TODO:
        // Format to correct currency
        String formattedValue = String.valueOf(value * pValue);

        subscriber.onNext(formattedValue);
    }
}
