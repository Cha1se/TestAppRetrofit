package com.cha1se.testappretrofit

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class CompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        var imgCompany: ImageView = findViewById(R.id.imgCompany)
        var nameCompany: TextView = findViewById(R.id.nameCompany)
        var decriptionCompany: TextView = findViewById(R.id.descriptionCompany)
        var coordinatsCompany: TextView = findViewById(R.id.coordinatsCompany)
        var siteCompany: TextView = findViewById(R.id.siteCompany)
        var phoneComapny: TextView = findViewById(R.id.phoneCompany)

        var initList: ArrayList<String> = ArrayList()

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy);

        initList = intent.getStringArrayListExtra("listCompanydescription")!!
        nameCompany.text = initList[1]

        Picasso.with(this)
            .load("https://lifehack.studio/test_task/" + initList[2])
            .into(imgCompany)

        decriptionCompany.text = initList[3]
        if (initList[4].equals("0")) {
            coordinatsCompany.text = "There are no coordinates"
        } else {
            coordinatsCompany.text = "Lat: ${initList[4]}, Lon: ${initList[5]}"
        }
        if (initList[6].equals("")){
            siteCompany.text = "There are no site"
        } else {
            siteCompany.text = initList[6]
        }
        if (initList[7].equals("")) {
            phoneComapny.text = "There are no phone"
        } else {
            phoneComapny.text = initList[7]
        }

    }
}