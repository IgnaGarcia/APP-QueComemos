package com.ignagr.quecomemos.remote

import com.ignagr.quecomemos.entities.Enums
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.remote.request.FoodRequest
import com.ignagr.quecomemos.remote.util.ApiLogger
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        private var apiRequest: ApiRequest? = null
        private var sRestAdapter: Retrofit? = null
        private const val BASE_URL = "https://qcomemos.herokuapp.com/"

        fun getInstance(): ApiRequest {
            if (apiRequest == null) {
                val loggingInterceptor = HttpLoggingInterceptor(ApiLogger())
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val httpClient = getOkHttpClient(loggingInterceptor)

                sRestAdapter = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()

                apiRequest = sRestAdapter!!.create(ApiRequest::class.java)
            }

            return apiRequest!!
        }

        fun retrofit(): Retrofit {
            return sRestAdapter!!
        }

        private fun getOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()
        }

        interface ApiRequest {

            @GET("foods")
            fun getFoods(@Query("id") id: String?,
                         @Query("type") type: String?,
                         @Query("culture") culture: String?,
                         @Query("hot") hot: String?
            ): Single<List<Food>>

            @POST("foods")
            fun createFood(@Body req: FoodRequest):
                    Single<Food>

            @GET("foods/enums")
            fun getEnums(): Single<Enums>
        }
    }
}