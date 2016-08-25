package com.rendecano.cashmoney.data.network;

public interface RestApi {

    String API_BASE_URL = "http://api.fixer.io/latest";

    void getExchangeRate(String baseCurrency, String[] symbols, JsonResponseListener listener);
}
