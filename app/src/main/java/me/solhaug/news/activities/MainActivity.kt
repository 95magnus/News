package me.solhaug.news.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.solhaug.news.adapters.NewsCardAdapter
import me.solhaug.news.models.Article
import me.solhaug.news.models.ArticleSource

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.solhaug.news.R
import me.solhaug.news.Utils
import me.solhaug.news.api.NewsManager
import me.solhaug.news.api.RestApi
import me.solhaug.news.models.NewsResponse
import me.solhaug.news.ui.SlideUpItemAnimator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var compositeDisposable: CompositeDisposable
    private var currentCategory: String = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        compositeDisposable = CompositeDisposable()

        fab.setOnClickListener { view ->
            Toast.makeText(applicationContext, "Fab pressed", Toast.LENGTH_SHORT)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.itemAnimator = SlideUpItemAnimator()
        recycler_view.adapter = NewsCardAdapter()

        refresh_layout.setOnRefreshListener{ onRefresh() }

        onRefresh()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onRefresh() {
        Utils.log("Loading latest news...")

        compositeDisposable?.add(
                RestApi(this).getTopStories(category = currentCategory)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(newsResponse: NewsResponse) {
        Utils.log("Handling response...")

        if (newsResponse.status == "ok") {
            val articles = newsResponse.articles

            Utils.log("Response code ok")

            (recycler_view.adapter as NewsCardAdapter).updateArticles(articles)

            recycler_view.smoothScrollToPosition(0)

            Utils.log("Latest news loaded")
        } else {
            Utils.log("Fetching news failed: Status: ${newsResponse.status}")
            Toast.makeText(this, "Fetching news failed: Status: ${newsResponse.status}", Toast.LENGTH_LONG).show()
        }

        refresh_layout.isRefreshing = false
    }

    private fun handleError(error: Throwable) {
        Utils.log("Fetching news failed: Status: ${error.message}")
        Toast.makeText(this, "Fetching news failed: ${error.message}", Toast.LENGTH_LONG).show()

        refresh_layout.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_refresh -> {
                refresh_layout.isRefreshing = true
                onRefresh()
                return true
            }
            R.id.action_settings -> {
                recycler_view.adapter = NewsCardAdapter()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_cat_business -> {
                currentCategory = "business"
                onRefresh()
            }
            R.id.nav_cat_entertainment-> {
                currentCategory = "entertainment"
                onRefresh()
            }
            R.id.nav_cat_general -> {
                currentCategory = "general"
                onRefresh()
            }
            R.id.nav_cat_health -> {
                currentCategory = "health"
                onRefresh()
            }
            R.id.nav_cat_science -> {
                currentCategory = "science"
                onRefresh()
            }
            R.id.nav_cat_sports -> {
                currentCategory = "sports"
                onRefresh()
            }
            R.id.nav_cat_technology -> {
                currentCategory = "technology"
                onRefresh()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
