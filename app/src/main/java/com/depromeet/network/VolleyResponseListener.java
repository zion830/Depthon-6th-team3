package com.depromeet.network;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyResponseListener {
    void onSuccess(JSONObject response);

    void onError(VolleyError error);
}
