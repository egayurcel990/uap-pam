package network

import model.DefaultResponse
import model.TanamanRequest
import model.TanamanResponse
import model.TanamanSingleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TanamanApi {
    @GET("plant/all")
    fun getAllTanaman(): Call<TanamanResponse>

    @GET("plant/{name}")
    fun getTanamanByName(@Path("name") name: String): Call<TanamanSingleResponse>

    @POST("plant/new")
    fun addTanaman(@Body request: TanamanRequest): Call<DefaultResponse>

    @PUT("plant/{name}")
    fun updateTanaman(@Path("name") originalName: String, @Body request: TanamanRequest): Call<DefaultResponse>

    @DELETE("plant/{name}")
    fun deleteTanaman(@Path("name") name: String): Call<DefaultResponse>
}