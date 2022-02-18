package com.ignagr.quecomemos.remote.repository

import com.ignagr.quecomemos.entities.Enums
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.remote.ApiClient
import com.ignagr.quecomemos.remote.request.FoodRequest
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FoodRepository {

    fun getFoods(id: String?, type: String?,
                 culture: String?, hot: Boolean?): Single<List<Food>> {
        return ApiClient.getInstance().getFoods(id, type, culture, hot)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getEnums(): Single<Enums> {
        return ApiClient.getInstance().getEnums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createFood(req: FoodRequest): Single<Food> {
        return ApiClient.getInstance().createFood(req)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}