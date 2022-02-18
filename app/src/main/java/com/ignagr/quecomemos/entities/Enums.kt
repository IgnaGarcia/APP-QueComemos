package com.ignagr.quecomemos.entities

import com.google.gson.annotations.SerializedName

class Enums(
    @SerializedName("types") val types: List<String>,
    @SerializedName("diets") val diets: List<String>,
    @SerializedName("cultures") val cultures: List<String>
)