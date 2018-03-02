package me.solhaug.news.adapters

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import kotlinx.android.extensions.LayoutContainer
import me.solhaug.news.models.Article

import kotlinx.android.synthetic.main.news_card.*

import me.solhaug.news.R
import me.solhaug.news.Utils
import me.solhaug.news.api.NewsManager
import me.solhaug.news.inflate
import me.solhaug.news.loadUrl

class NewsCardAdapter(): RecyclerView.Adapter<NewsCardAdapter.ViewHolder>() {

    var articles: List<Article>

    init {
        articles = listOf()//newsManager.tester()
    }

    class ViewHolder(override val containerView: View?): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(article: Article) {
            //val date = Utils.utcToGmt(article.publishedAt)

            news_title.text = article.title
            news_description.text = article.description
            news_source.text = article.source.name ?: ""
            news_published.text = article.publishedAt ?: ""
            news_image.loadUrl(article.urlToImage)

            news_open_browser.setOnClickListener {
                view ->
                if (!article.url.isNullOrEmpty())
                    view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
            }
        }
    }

    fun updateArticles(newArticles: List<Article>) {
        articles = newArticles

        notifyDataSetChanged()
    }

    fun appendArticles(newArticles: List<Article>) {
        val startIndex = articles.size - 1

        articles += newArticles

        notifyItemRangeChanged(startIndex, newArticles.size)
    }

    fun prependArticles(newArticles: List<Article>) {
        articles += newArticles
        // TODO: Finish

        notifyItemRangeChanged(0, newArticles.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.news_card)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
