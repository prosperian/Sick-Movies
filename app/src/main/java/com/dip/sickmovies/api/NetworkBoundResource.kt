package com.dip.sickmovies.api

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dip.sickmovies.AppExecutors
import com.dip.sickmovies.utils.Utils
import com.skydoves.sandwich.ApiResponse
import retrofit2.Call
import java.util.*

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    val result: MutableLiveData<Resource<ResultType?>> = MutableLiveData();

    @WorkerThread
    abstract fun loadFromDB(): LiveData<ResultType?>

    @MainThread
    abstract fun loadFromNetwork(): LiveData<ApiResponse<ResultType?>>

    @WorkerThread
    abstract fun getDataFetchDate(data: ResultType?): Long?

    @WorkerThread
    abstract fun saveCallResult(item: RequestType)

    private fun shouldFetch(data: ResultType?, dataFetchDate: Long?): Boolean {
        Log.d("Network resource", "out if");
        return if (data != null) {
            Log.d("Network resource", "in if");
            dataFetchDate.let {
                if (it != null) {
                    (Date().time - it) > Utils.TIME_TO_LIVE
                } else {
                    false
                }
            }
        } else {
            true
        }
    }

    fun execute() {
        Log.d("Network resource", "execute");

        appExecutors.diskIO().execute {
            Log.d("Network resource", "execute");

            val data = loadFromDB()
            val dataFetchDate = getDataFetchDate(data.value)

            result.postValue(Resource.loading(data = data.value))
            if (shouldFetch(data.value, dataFetchDate)) {
                loadFromNetwork()
                Log.d("Network resource", "from network");
            } else {
                Log.d("Network resource", "from db");
                result.postValue(Resource.success(data = data.value, date = dataFetchDate))
            }
        }

    }
}