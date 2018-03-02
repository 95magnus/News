package me.solhaug.news.api

import android.content.Context
import me.solhaug.news.models.Article
import me.solhaug.news.models.ArticleSource

class NewsManager(private val context: Context, private val api: RestApi = RestApi(context)) {

    fun tester(): List<Article> {
        val url = "https://gfx.nrk.no/B0umruOXQivtCo5TTO8FvgYsPkdUQGr6KH-1tf82HACA"
        val desc = "This is the test description which describes the content of the story"
        return listOf(
                Article(ArticleSource("", "NRK"), "NRK", "Title of article1", desc + 1, "", url, "testtime"),
                Article(ArticleSource("", "NRK"), "NRK", "Title of article2", desc + 2, "", url, "testtime"),
                Article(ArticleSource("", "NRK"), "NRK", "Title of article3", desc + 3, "", url, "testtime")
        )
    }

}
