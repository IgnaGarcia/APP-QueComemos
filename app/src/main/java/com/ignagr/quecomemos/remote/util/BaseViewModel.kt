package com.ignagr.quecomemos.remote.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val context: Context by lazy { getApplication<Application>().applicationContext }
    private var compositeDisposable = CompositeDisposable()

    var mDisposable: Disposable? = null
    private var loading: MutableLiveData<Boolean>? = null
    protected var error: MutableLiveData<String>? = null

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    init {
        loading = MutableLiveData()
        error = MutableLiveData()
    }

    fun onError(t: Throwable) {
        loading?.postValue(false)
        error?.postValue(ApiErrorParser(getApplication()).parseError(t))
    }

    fun showLoading(): LiveData<Boolean>? = loading

    fun showError(): LiveData<String>? = error
}