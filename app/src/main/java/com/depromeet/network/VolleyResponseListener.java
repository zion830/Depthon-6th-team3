package com.example.project.network;

import com.android.volley.VolleyError;

public interface VolleyResponseListener {
    void onSuccess(String response);

    void onError(VolleyError error);
}
