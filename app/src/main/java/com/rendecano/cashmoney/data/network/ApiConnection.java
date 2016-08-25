package com.rendecano.cashmoney.data.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiConnection {

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json;charset=utf-8";

    private URL url;
    private JsonResponseListener mListener;

    public static ApiConnection createGET(String url, JsonResponseListener listener) throws MalformedURLException {
        return new ApiConnection(url, listener);
    }

    private ApiConnection(String url, JsonResponseListener listener) throws MalformedURLException {
        this.url = new URL(url);
        this.mListener = listener;
    }

    public void call() {
        new RestConnectionTask().execute(url);
    }

    class RestConnectionTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            String currencies = "";
            HttpURLConnection urlConnection;
            try {

                urlConnection = (HttpURLConnection) params[0].openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                currencies = builder.toString();
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
                mListener.onError("Error in getting response");
            }
            return currencies;
        }

        @Override
        protected void onPostExecute(String response) {

            // Check if response is not empty
            if (!TextUtils.isEmpty(response)) {
                mListener.onSuccess(response);
            } else {
                mListener.onError("Error in getting response");
            }
        }
    }
}
