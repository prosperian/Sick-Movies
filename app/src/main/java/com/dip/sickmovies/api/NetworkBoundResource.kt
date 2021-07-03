package com.dip.sickmovies.api

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
        return if (data != null) {
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

        appExecutors.diskIO().execute {

            val data = loadFromDB()
            val dataFetchDate = getDataFetchDate(data.value)

            result.postValue(Resource.loading(data = data.value))
            if (shouldFetch(data.value, dataFetchDate)) {
                loadFromNetwork()
            } else {
                result.postValue(Resource.success(data = data.value, date = dataFetchDate))
            }
        }

    }
}