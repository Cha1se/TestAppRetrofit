package com.cha1se.testappretrofit.data.remote

data class TestItemList (
    val id: String,
    val name: String,
    val img: String
)

data class TestItemDescriptionList (
    val id: String,
    val name: String,
    val img: String,
    val description: String,
    val lat: String,
    val lon: String,
    val www: String,
    val phone: String
)