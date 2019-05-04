package com.example.project.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Locale;

public class VolleySingleton {
    private static final int PORT = 10000;
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private Context context;

    private VolleySingleton(Context c) {
        context = c;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null)
            instance = new VolleySingleton(context);

        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);

        return requestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    private String getUrl(String direction) {
        return String.format(Locale.getDefault(), "%s:%d/%s", RequestURL.BASE_URL, PORT, direction);
    }

    public void get(String direction, final VolleyResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getUrl(direction), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });

        instance.addToRequestQueue(request);
    }
}