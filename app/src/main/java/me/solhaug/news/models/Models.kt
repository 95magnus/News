package me.solhaug.news.models

class NewsResponse(
        val status: String,
        val totalResults: Int,
        val articles: List<Article>
)

class Article(
        val source: ArticleSource,
        val author: String?,
        val title: String,
        val description: String,
        val url: String?,
        val urlToImage: String?,
        val publishedAt: String?
)

class ArticleSource(val id: String?, val name: String?)
