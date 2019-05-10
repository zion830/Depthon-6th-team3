package com.depromeet.network

import com.depromeet.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApi {
    @POST("login")
    fun userLogin(@Body user: User): Call<LoginResponse>

    @GET("hangshis/create")
    fun getRandomWord(): Call<WordResponse>

    @POST("hangshis/save_hangshi")
    fun savePoem(@Body poem: Poem): Call<BasicResponse>

    @GET("hangshis/get_by_date")
    fun getByDate(@Query("page") page: Int,
                  @Query("userId") userId: Int): Call<List<Poem>>

    @GET("hangshis/get_by_like")
    fun getByLike(@Query("page") page: Int,
                  @Query("userId") userId: Int): Call<List<Poem>>
}