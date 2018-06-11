package com.example.carlos.testandroid;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    RequestQueue queue;

    private static String contactUrl = "https://floating-mountain-50292.herokuapp.com/cells.json";
    private JSONObject contactJson;

    private static String investUrl = "https://floating-mountain-50292.herokuapp.com/fund.json";
    private JSONObject investJson;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest getContactRequest = new JsonObjectRequest(Request.Method.GET, contactUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        contactJson = response;
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", response);
                    }
                }
        );

        JsonObjectRequest getInvestRequest = new JsonObjectRequest(Request.Method.GET, investUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        investJson = response;
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", response);
                    }
                }
        );

        queue.add(getContactRequest);
        queue.add(getInvestRequest);
    }

    public JSONObject GetContactJson(){

        return contactJson;
    }

    public JSONObject GetInvestJson(){

        return investJson;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
}