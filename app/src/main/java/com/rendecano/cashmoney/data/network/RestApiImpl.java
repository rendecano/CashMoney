package com.rendecano.cashmoney.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;

public class RestApiImpl implements RestApi {

    private final Context context;
    private JsonResponseListener mJsonResponseListener;

    public RestApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null.");
        }
        this.context = context.getApplicationContext();
    }

    @Override
    public void getExchangeRate(String baseCurrency, String[] symbols, JsonResponseListener listener) {
        mJsonResponseListener = listener;

        if (isThereInternetConnection()) {
            try {
                getExchangeRatesFromApi(baseCurrency, symbols);
            } catch (MalformedURLException e) {
                mJsonResponseListener.onError("Malformed URL");
            }

        } else {
            mJsonResponseListener.onError("No internet connection detected");
        }
    }

    private void getExchangeRatesFromApi(String baseCurrency, String[] symbols) throws MalformedURLException {
        String apiUrl = RestApi.API_BASE_URL + "?base=" + baseCurrency + "&symbols=";

        for (String symbol : symbols) {
            apiUrl += symbol + ",";
        }

        ApiConnection.createGET(apiUrl, mJsonResponseListener).call();
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
