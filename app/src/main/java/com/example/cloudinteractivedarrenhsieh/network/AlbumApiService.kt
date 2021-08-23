package com.example.cloudinteractivedarrenhsieh.network

import com.example.cloudinteractivedarrenhsieh.data.Album
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val JsonUrl = "https://jsonplaceholder.typicode.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(JsonUrl)
    .build()


interface AlbumApiService {

    @GET("photos/")
    suspend fun getAlbum():Response<List<Album>>

}

object AlbumApi{
    val retrofitService: AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java)
    }
}