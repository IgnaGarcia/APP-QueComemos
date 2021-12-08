package com.ignagr.quecomemos.entities

class Filter(
    val apply: Boolean = false,
    val isHot: Boolean?,
    val diet: List<String>?,
    val type: String?
){

    fun evaluate(food: Food): Boolean {
        if(isHot != null && isHot != food.isHot) return false
        if(type != null && type != food.type) return false
        if(!diet.isNullOrEmpty() && !dietsWithIntersection(diet, food.diet!!)) return false

        return true
    }

    private fun dietsWithIntersection(l1: List<String>, l2: List<String>): Boolean{
        l1.forEach {
            if(l2.contains(it)) return true
        }
        return false
    }
}