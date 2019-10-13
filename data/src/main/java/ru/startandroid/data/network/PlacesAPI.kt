package ru.startandroid.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.startandroid.data.BuildConfig
import ru.startandroid.domain.models.placesmodel.citiesdetailsmodels.CityDetails
import ru.startandroid.domain.models.placesmodel.citiesrequestmodels.CitiesRequestResult


interface PlacesAPI {

    @GET("autocomplete/json")
    fun getPlaces(@Query("input") input: String, @Query("types") types: String = CITIES_TYPE): Call<CitiesRequestResult>

    @GET("details/json")
    fun getPlaceDetails(@Query("placeid") placeid: String, @Query("language") language: String): Call<CityDetails>

    companion object Factory {

        val CITIES_TYPE = "(cities)"

        fun create(): PlacesAPI {
            val clientBuilder = OkHttpClient.Builder()

            val headerAuthorizationInterceptor = Interceptor { chain ->
                var request: Request = chain.request()
                val url = request.url.newBuilder()
                        .addQueryParameter("key", "AIzaSyDJrJtQwO2w3OCSzXSLiNP_XUgJtujnov")
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

            clientBuilder
                    .addInterceptor(headerAuthorizationInterceptor)
                    .addInterceptor(loggingInterceptor)

            val retrofit = Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/place/")
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(PlacesAPI::class.java)
        }
    }

}