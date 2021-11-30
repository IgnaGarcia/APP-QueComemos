package com.ignagr.quecomemos.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.ignagr.quecomemos.entities.Food

class FirestoreClient {
    private val db = FirebaseFirestore.getInstance()
    private val FOOD_COLLECTION = "food"

    fun saveFood(food: Food) {
        val parsed = food.toHashMap()
        if(parsed != null){
            db.collection(FOOD_COLLECTION)
                .document(food.name!!)
                .set(parsed)
        }
    }

    fun getFoodList(): Task<QuerySnapshot> {
        return db.collection(FOOD_COLLECTION).get()
    }
}