package com.rendecano.cashmoney.data.network;

public interface JsonResponseListener {

    void onSuccess(String response);

    void onError(String message);
}
