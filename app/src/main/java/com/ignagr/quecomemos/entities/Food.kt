package com.ignagr.quecomemos.entities

import com.google.firebase.firestore.DocumentSnapshot

data class Food(
    val name: String? = "",
    val type: String? = "",
    val diet: List<String>? = listOf(),
    val isHot: Boolean? = false
) {

    companion object{
        fun toFood(snapshot: DocumentSnapshot): Food{
            return Food(
                snapshot.getString("name"),
                snapshot.getString("type"),
                snapshot.get("diet") as List<String>,
                snapshot.getBoolean("isHot")
            )
        }
    }

    fun getTags(): String {
        val temp = if (isHot == true) "Caliente" else "Frio"
        val diets = diet?.joinToString(", ")
        return "$type - $temp\n$diets"
    }

    fun isNull(): Boolean{
        return name.isNullOrBlank() || type.isNullOrBlank() || diet.isNullOrEmpty()
    }

    fun toHashMap(): HashMap<String, Any>? {
        if(!isNull()){
            return hashMapOf(
                "name" to name!!,
                "type" to type!!,
                "diet" to diet!!,
                "isHot" to isHot!!
            )
        } else return null
    }

    override fun toString() : String{
        return "Food(name= ${name},\n\tisHot=${isHot},\n\ttype=${type},\n\tdiets=${diet.toString()}"
    }
}