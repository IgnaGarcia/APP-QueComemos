package com.ignagr.quecomemos.ui.main.newFood

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ignagr.quecomemos.entities.Enums
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.remote.repository.FoodRepository
import com.ignagr.quecomemos.remote.request.FoodRequest
import com.ignagr.quecomemos.remote.util.BaseViewModel

class NewFoodViewModel(application: Application) : BaseViewModel(application) {

    private var foodRepository = FoodRepository()

    private var createFoodLive = MutableLiveData<Food>()
    fun obsNewFood(): MutableLiveData<Food> = createFoodLive

    fun create(food: FoodRequest){
        mDisposable = foodRepository.createFood(food)
            .subscribe(this::onCreateSucces, this::onError)
    }
    fun onCreateSucces(new: Food){
        createFoodLive.postValue(new)
    }


    private var enumsLive = MutableLiveData<Enums>()
    fun obsEnums(): MutableLiveData<Enums> = enumsLive

    fun getEnums(){
        mDisposable = foodRepository.getEnums()
            .subscribe(this::onGetEnums, this::onError)
    }
    fun onGetEnums(enums: Enums){
        enumsLive.postValue(enums)
    }
}