package me.solhaug.news.api

import io.reactivex.Observable
import me.solhaug.news.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines?country=no")
    fun getTopStories(@Query("page") page: Int, @Query("category") category: String): Observable<NewsResponse>
}