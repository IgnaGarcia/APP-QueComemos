package com.ignagr.quecomemos.entities

class Selection(
    var open: Boolean = true,
    val vote: List<Food>) {

    fun changeVote(food: Food, sum: Boolean){
        if(open) {
            for((i, f) in vote.withIndex()){
                if(f == food) {
                    vote[i].votes =
                        if (sum) vote[i].votes!! + 1
                        else vote[i].votes!! - 1
                    break
                }
            }
        }
    }
}