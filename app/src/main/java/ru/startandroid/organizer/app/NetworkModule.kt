package ru.startandroid.organizer.app

import java.io.IOException

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideNetwork(): Retrofit {

        val clientBuilder = OkHttpClient.Builder()

        //Create a new Interceptor.
        val headerAuthorizationInterceptor = Interceptor { chain ->
            var request: okhttp3.Request = chain.request()
            val headers = request.headers().newBuilder().add("key", "405f4e5b916047bd9f4193926181511").build()
            request = request.newBuilder().headers(headers).build()
            chain.proceed(request)
        }

        clientBuilder.addInterceptor(headerAuthorizationInterceptor)

        return Retrofit.Builder().baseUrl("http://api.apixu.com/v1/forecast.json")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

}
