package com.depromeet.network

import com.depromeet.data.*
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {
    @POST("login")
    fun userLogin(@Body user: User): Call<LoginResponse>

    @GET("hangshis/create")
    fun getRandomWord(): Call<WordResponse>

    @POST("hangshis/save_hangshi")
    fun savePoem(@Body poem: PoemRequest): Call<BasicResponse>

    @GET("hangshis/get_by_date")
    fun getByDate(@Query("page") page: Int,
                  @Query("userId") userId: Int): Call<List<Poem>>

    @GET("hangshis/get_by_like")
    fun getByLike(@Query("page") page: Int,
                  @Query("userId") userId: Int): Call<List<Poem>>

    @GET("/user/{userId}/get_hangshi")
    fun getMyPoems(@Path("userId") userId: Int,
                   @Query("page") page: Int): Call<List<Poem>>

    @POST("/hangshis/like")
    fun increaseLike(@Body item: LikeRequest): Call<BasicResponse>

    @POST("/hangshis/unlike")
    fun decreaseLike(@Body item: LikeRequest): Call<BasicResponse>
}