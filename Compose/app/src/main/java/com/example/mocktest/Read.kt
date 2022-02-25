package com.example.mocktest

import com.squareup.moshi.Json

data class Read(
    @Json(name = "problem")
    val problem : String,
    @Json(name = "examples1")
    val examples1 : String,
    @Json(name = "examples2")
    val examples2 : String,
    @Json(name = "examples3")
    val examples3 : String,
    @Json(name = "examples4")
    val examples4 : String,
    @Json(name = "examples5")
    val examples5 : String,
    @Json(name = "explanation")
    val explanation : String,
    @Json(name = "answer")
    val answer : String,
    @Json(name = "video")
    val video : Boolean,
    @Json(name = "image")
    val image : Boolean
)