package com.sahmed.forecaster.framework.network


import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestClient {

    fun getInstance(): APIs {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIs::class.java)
    }

    private fun getInterceptor(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", Constants.API_KEY)
                .addQueryParameter("units",Constants.CONFIGURED_UNITS_FORMAT)
                .build()
            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }

        return httpClient.build()
    }
}