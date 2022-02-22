package com.ignagr.quecomemos.entities

class Filter(
    val apply: Boolean = false,
    val isHot: Boolean? = null,
    val diet: List<String>? = null,
    val type: String? = null,
    val culture: String? = null
){

    fun evaluate(food: Food): Boolean {
        return !(!diet.isNullOrEmpty()
                && !dietsWithIntersection(diet, food.diet))
    }

    private fun dietsWithIntersection(l1: List<String>, l2: List<String>): Boolean{
        l1.forEach {
            if(l2.contains(it)) return true
        }
        return false
    }
}