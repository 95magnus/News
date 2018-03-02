package me.solhaug.news.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("News", "Splash starting")

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}