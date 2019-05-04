package com.depromeet.network;

import java.util.Locale;

public class RequestURL {
    public static final String BASE_URL = "http://3.16.157.244:8080";
    public static final String LOGIN = "login";
    public static final String CREATE_POEM = "hangshis/create";
    public static final String GET_BY_DATE = "hangshis/get_by_date?page=%d&&userId=%d";
    public static final String GET_BY_LIKE = "hangshis/get_by_like?page=%d&&userId=%d";
    public static final String LIKE = "hangshis/like";
    public static final String UNLIKE = "hangshis/unlike";
    public static final String SAVE_POEM = "hangshis/save_hangshi";

    public static String getPoemsByDateUrl(int page, int userId) {
        return String.format(Locale.getDefault(), GET_BY_DATE, page, userId);
    }

    public static String getPoemsByLikeUrl(int page, int userId) {
        return String.format(Locale.getDefault(), GET_BY_LIKE, page, userId);
    }
}
