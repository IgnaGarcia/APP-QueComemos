package com.ignagr.quecomemos.entities

class Selection(
    var open: Boolean = true,
    val vote: List<Food>) {

    fun voteTo(idx: Int){
        if(open) vote[idx].votes = vote[idx].votes?.plus(1)
    }

    fun voteTo(idxList: List<Int>){
        if(open) idxList.forEach { voteTo(it) }
    }
}