package com.depromeet.network;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface VolleyArrayResponseListener {
    void onSuccess(JSONArray response);

    void onError(VolleyError error);
}
