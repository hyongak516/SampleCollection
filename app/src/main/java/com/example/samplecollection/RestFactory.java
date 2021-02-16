package com.example.samplecollection;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

interface IResult {
    void completion(int responseCode, JSONObject jsonObject) throws JSONException;
}

public class RestFactory {
    public static final int    RESPONSE_TIMEOUT = 30 * 1000; //Consider Perks Card API speed, yunshok.ahn increase to 30 sec due to Waterloo Mastercard lab data environment


    public static void requestRestApi(final URL fullUrl, final String httpBody, final String requestMethod, final IResult result) {
        new Thread() {
            public void run() {
                HttpURLConnection urlConnection;
                try {
                    urlConnection = (HttpURLConnection)fullUrl.openConnection();
                    if (urlConnection != null) {
                        urlConnection.setConnectTimeout(RESPONSE_TIMEOUT);
                        urlConnection.setReadTimeout(RESPONSE_TIMEOUT);
                        //urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        urlConnection.setRequestProperty("Accept", "application/json");

                        urlConnection.setRequestMethod(requestMethod);
                        if (httpBody != null) {
                            byte[] postDataBytes = httpBody.getBytes("UTF-8");
                            urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                            urlConnection.getOutputStream().write(postDataBytes);
                            urlConnection.getOutputStream().close();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return ;
                }

                try {
                    int responseCode = urlConnection.getResponseCode();

                    if(responseCode == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                            Log.d("kihoon.kim", ""+line);
                        }
                        br.close();
                        JSONObject json = new JSONObject(sb.toString());
                        result.completion(responseCode, json);
                    }
                } catch (SocketTimeoutException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
