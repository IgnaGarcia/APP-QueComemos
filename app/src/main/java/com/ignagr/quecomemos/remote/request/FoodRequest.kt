package com.ignagr.quecomemos.remote.request

import com.google.gson.annotations.SerializedName

class FoodRequest(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("diet") val diet: List<String>,
    @SerializedName("culture") val culture: String?,
    @SerializedName("hot") val isHot: Boolean
)