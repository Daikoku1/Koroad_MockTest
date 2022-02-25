package com.example.mocktest

import com.github.theapache64.retrosheet.annotations.Write
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    // @Read("SELECT * ORDER BY created_at DESC")
    @GET("Koroad_Quiz")
    fun getNotes(): Call<List<Read>> // you can also use suspend and return List<Note>

//    @Read("SELC")
//    @GET("Koroad_Quiz")
//    fun getNote(): Call<List<Read>> // you can also use suspend and return List<Note>

}