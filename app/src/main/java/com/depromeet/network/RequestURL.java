package com.depromeet.network;

import java.util.Locale;

public class RequestURL {
    static final String BASE_URL = "https://6fe68e2c.ngrok.io/";
    static final String LOGIN = "login";

    static String getUrl(String direction) {
        return String.format(Locale.getDefault(), "%s/%s", BASE_URL, direction);
    }
}
