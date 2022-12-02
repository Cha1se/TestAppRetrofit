package com.cha1se.testappretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.ImageView
import android.widget.TextView
import com.cha1se.testappretrofit.data.remote.TestAPI
import com.cha1se.testappretrofit.data.remote.TestItemList
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy);

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, BlankFragment())
            .commit()
    }
}