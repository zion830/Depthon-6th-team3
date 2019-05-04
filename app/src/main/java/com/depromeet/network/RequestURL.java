package com.example.project.network;

import java.util.Locale;

public class RequestURL {
    static final String BASE_URL = "http://boostcourse-appapi.connect.or.kr";
    public static final String GET_MOVIE_LIST = "movie/readMovieList";
    public static final String GET_MOVIE = "movie/readMovie?id=";
    public static final String GET_REVIEW_LIST = "movie/readCommentList?id=%d&&limit=%s";
    public static final String CREATE_REVIEW = "movie/createComment?id=%d&&writer=%s&&rating=%.1f&&contents=%s";
    public static final String UPDATE_REVIEW_RECOMMEND = "movie/increaseRecommend?review_id=%d";
    private static final String UPDATE_LIKE = "movie/increaseLikeDisLike?id=%d&&likeyn=%s";
    private static final String UPDATE_DISLIKE = "movie/increaseLikeDisLike?id=%d&&dislikeyn=%s";

    public static String getMovieUrl(int movieId) {
        return GET_MOVIE + movieId;
    }

    public static String getReviewUrl(int movieId, int max) {
        return String.format(Locale.getDefault(), GET_REVIEW_LIST, movieId, max);
    }

    public static String getUpdateGoodUrl(int movieId, int good) {
        String yOrN = "";
        if (good != 0) {
            yOrN = (good == 1) ? "Y" : "N";
        }

        return String.format(Locale.getDefault(), UPDATE_LIKE, movieId, yOrN);
    }

    public static String getUpdateBadUrl(int movieId, int bad) {
        String yOrN = "";
        if (bad != 0) {
            yOrN = (bad == 1) ? "Y" : "N";
        }

        return String.format(Locale.getDefault(), UPDATE_DISLIKE, movieId, yOrN);
    }
}
