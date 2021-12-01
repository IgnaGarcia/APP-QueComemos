package com.ignagr.quecomemos.entities

class Selection(
    val open: Boolean = true,
    items: List<Food>) {
    val vote: MutableMap<Food, Int> = mutableMapOf()

    init {
        items.forEach {
            vote[it] = 0
        }
    }

    fun voteTo(food: Food){
        if(open) vote[food]?.plus(1)
    }

    fun voteTo(foodList: List<Food>){
        if(open) foodList.forEach { voteTo(it) }
    }
}