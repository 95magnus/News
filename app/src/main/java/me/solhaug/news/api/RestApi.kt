package me.solhaug.news.api

import android.content.Context
import io.reactivex.Observable
import me.solhaug.news.R
import me.solhaug.news.models.NewsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val API_URL = "https://newsapi.org/v2/"

class RestApi(context: Context) {

    private val newsApi: NewsApi

    init {
        val apiKey = context.getString(R.string.api_key_news)

        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.networkInterceptors().add( Interceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Authorization", apiKey).build()

            chain.proceed(request)
        })

        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        newsApi = retrofit.create(NewsApi::class.java)
    }

    fun getTopStories(page: Int = 1, category: String = "general"): Observable<NewsResponse> {
        return newsApi.getTopStories(page, category)
    }
}