package com.ignagr.quecomemos.remote.util

import android.content.Context
import com.ignagr.quecomemos.R
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

class ApiErrorParser(val context: Context) {
    fun parseError(error: Throwable): String {
        return when (error) {
            is HttpException -> {
                if(error.response()?.code() == 422){
                    var str = ""
                    val response = JSONObject(error.response()!!.errorBody()?.string())
                    response.keys().forEach {
                        if(response[it] is JSONArray){
                            str = "$str${(response[it] as JSONArray)[0]}\n"
                        } else str = "$str${response[it]}\n"
                    }
                    return str
                }
                else if(error.response()?.code()  == 404){
                    return context.getString(R.string.not_found)
                }
                else return error.response()!!.errorBody()?.string().toString()
            }
            is SocketTimeoutException -> context.getString(R.string.unknown_error)
            else -> context.getString(R.string.unknown_error)
        }
    }
}