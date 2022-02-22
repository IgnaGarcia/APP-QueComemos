package com.ignagr.quecomemos.entities

import com.google.gson.annotations.SerializedName

class Food(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("diet") val diet: List<String> = listOf(),
    @SerializedName("culture") val culture: String,
    @SerializedName("hot") val isHot: Boolean = false,
    var votes: Int = 0
) {

    fun getTags(): String {
        val diets = diet.joinToString(", ")
        return "$type - $culture\n$diets"
    }

    fun isNull(): Boolean{
        return name.isNullOrBlank() || type.isNullOrBlank() || diet.isNullOrEmpty()
    }

    fun toHashMap(): HashMap<String, Any>? {
        if(!isNull()){
            return hashMapOf(
                "name" to name,
                "type" to type,
                "diet" to diet,
                "isHot" to isHot
            )
        } else return null
    }

    override fun toString() : String{
        return "Food(name= ${name},\n\tisHot=${isHot},\n\ttype=${type},\n\tdiets=${diet},\n\tculture=${culture}"
    }

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other !is Food) return false
        return other.name == name && other.isHot == isHot && other.type == type
                && diet == other.diet && other.culture == culture
    }
}