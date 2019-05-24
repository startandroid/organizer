package ru.startandroid.data.network

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.startandroid.data.BuildConfig
import ru.startandroid.domain.models.WeatherData



interface WeatherAPI {

    @GET("forecast.json")
    fun getCityWeather(@Query("q") q: String, @Query("days") days: String): Observable<WeatherData>

    companion object Factory {
        fun create(): WeatherAPI {
            val clientBuilder = OkHttpClient.Builder()

            val headerAuthorizationInterceptor = Interceptor { chain ->
                var request: Request = chain.request()
                val url = request.url().newBuilder().
                        addQueryParameter("key", "405f4e5b916047bd9f4193926181511").build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.
                    Level.BODY else HttpLoggingInterceptor.Level.NONE

            clientBuilder
                    .addInterceptor(headerAuthorizationInterceptor)
                    .addInterceptor(loggingInterceptor)

            val retrofit = Retrofit.Builder().baseUrl("https://api.apixu.com/v1/")
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(WeatherAPI::class.java)
        }
    }
}




