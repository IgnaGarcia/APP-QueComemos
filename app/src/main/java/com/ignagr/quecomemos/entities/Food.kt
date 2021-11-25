package com.ignagr.quecomemos.entities

class Food(
    val name: String,
    val type: String,
    val isHot: Boolean,
    val diet: String
) {

    fun getTags(): String {
        val temp = if (isHot) "Caliente" else "Frio"
        return "$type - $diet - $temp"
    }
}