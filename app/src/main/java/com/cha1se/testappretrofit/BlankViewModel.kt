package com.cha1se.testappretrofit

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.cha1se.testappretrofit.data.remote.TestAPI
import com.cha1se.testappretrofit.data.remote.TestItemList
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*


class BlankViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    public lateinit var itemListCompany: List<TestItemList>

    public val IdLay = 3000
    public val IdName = 4000
    public val IdImg = 5000

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchTestList(testAPI: TestAPI, context: FragmentActivity?) {

        compositeDisposable.add(
            testAPI.getListItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    for (i in 0..it.count()-1) {
                        autocreateCardCompany(context,
                            it[i].id.toString().toInt(),
                            it[i].name.toString(),
                            "https://lifehack.studio/test_task/" + it[i].img.toString())
                    }

                    Log.e("TAG", ("-------${it.first().name.toString()}"))
                }, {
                    Log.e("TAG", it.message.toString())
                })
        )
    }

    fun fetchDescriptionList(testAPI: TestAPI, idCompany: String, context: Context) {
        var listCompanydescription: ArrayList<String> = ArrayList()
        compositeDisposable.add(
            testAPI.getCompanyDescription("test.php?id=" + idCompany).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("TAG", it.first().id.toString())
                    listCompanydescription.add(it.first().id.toString())
                    listCompanydescription.add(it.first().name.toString())
                    listCompanydescription.add(it.first().img.toString())
                    listCompanydescription.add(it.first().description.toString())
                    listCompanydescription.add(it.first().lat.toString())
                    listCompanydescription.add(it.first().lon.toString())
                    listCompanydescription.add(it.first().www.toString())
                    listCompanydescription.add(it.first().phone.toString())

                    var intent = Intent(context, CompanyActivity::class.java)
                    intent.putExtra("listCompanydescription", listCompanydescription)
                    context.startActivity(intent)

                    Log.e("TAG", it.first().name.toString())

                }, {
                    Log.e("TAG", it.message.toString())
                    Toast.makeText(context, "This page is not defined", Toast.LENGTH_SHORT).show()
                })
        )
    }

    fun autocreateCardCompany(context: FragmentActivity?, ID: Int, textName: String, imgUri: String) {
        var contextApp = context!!.application
        var baseLay: LinearLayout = context.findViewById(R.id.baseLay)

        var mainLay: ConstraintLayout = ConstraintLayout(contextApp)
        var image: ImageView = ImageView(contextApp)
        var name: TextView = TextView(contextApp)

        baseLay.addView(mainLay, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mainLay.addView(image)
        mainLay.addView(name)

        mainLay.apply {
            id = IdLay + ID
//            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            setBackgroundColor(Color.parseColor("#CE93D8"))
        }

        image.apply {
            id = IdImg + ID
            layoutParams = ConstraintLayout.LayoutParams(dpToPx(100, context),dpToPx(100, context))
            scaleType = ImageView.ScaleType.FIT_XY
        }

        Picasso.with(contextApp)
            .load(imgUri)
            .into(image)

        name.apply {
            id = IdName + ID
            text = textName
            textSize = 20f
            setTextColor(Color.WHITE)
            typeface = ResourcesCompat.getFont(contextApp, R.font.roboto)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        var nameParams = ConstraintSet()
        nameParams.clone(mainLay)

        nameParams.setMargin(mainLay.id, ConstraintSet.BOTTOM, dpToPx(10, context))
        nameParams.setMargin(mainLay.id, ConstraintSet.TOP, dpToPx(10, context))
        nameParams.connect(image.id, ConstraintSet.LEFT, mainLay.id, ConstraintSet.LEFT)
        nameParams.connect(image.id, ConstraintSet.TOP, mainLay.id, ConstraintSet.TOP)
        nameParams.connect(image.id, ConstraintSet.BOTTOM, mainLay.id, ConstraintSet.BOTTOM)

        nameParams.connect(name.id, ConstraintSet.LEFT, image.id, ConstraintSet.RIGHT)
        nameParams.connect(name.id, ConstraintSet.TOP, mainLay.id, ConstraintSet.TOP)
        nameParams.connect(name.id, ConstraintSet.RIGHT, mainLay.id, ConstraintSet.RIGHT)
        nameParams.connect(name.id, ConstraintSet.BOTTOM, mainLay.id, ConstraintSet.BOTTOM)
        nameParams.applyTo(mainLay)

        mainLay.setOnClickListener(View.OnClickListener {

            (contextApp as? TestApp)?.let { fetchDescriptionList(it.testAPI, ID.toString(), contextApp) }!! //fetchDescriptionList(contextApp as TestApp.TestAPI, context, ID)!!

        })
    }

    fun dpToPx(dp: Int, context: FragmentActivity?): Int {
        val displayMetrics: DisplayMetrics = context!!.application.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}