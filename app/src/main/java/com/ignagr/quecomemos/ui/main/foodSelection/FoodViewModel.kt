package com.ignagr.quecomemos.ui.main.foodSelection

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ignagr.quecomemos.entities.Enums
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.remote.repository.FoodRepository
import com.ignagr.quecomemos.remote.request.FoodRequest
import com.ignagr.quecomemos.remote.util.BaseViewModel

class FoodViewModel(application: Application) : BaseViewModel(application) {

    private var foodRepository = FoodRepository()

    private var getFoodLive = MutableLiveData<List<Food>>()
    fun obsList(): MutableLiveData<List<Food>> = getFoodLive

    fun getList(id: String? = null, type: String? = null,
                culture: String? = null, hot: Boolean? = null){
        mDisposable = foodRepository.getFoods(id, type, culture, hot)
            .subscribe(this::onGetListSucces, this::onError)
    }
    fun onGetListSucces(list: List<Food>){
        getFoodLive.postValue(list)
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