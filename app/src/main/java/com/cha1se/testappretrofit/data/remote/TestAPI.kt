package com.cha1se.testappretrofit.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface TestAPI {

    @GET("test.php")
    fun getListItems(): Single<List<TestItemList>>

    @GET
    fun getCompanyDescription(@Url url: String): Single<List<TestItemDescriptionList>>

}