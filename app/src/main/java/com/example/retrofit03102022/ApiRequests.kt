package com.example.retrofit03102022

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {
    @GET("fact?max_length=1000")
    fun getFact(): Call<CatJSON>
}