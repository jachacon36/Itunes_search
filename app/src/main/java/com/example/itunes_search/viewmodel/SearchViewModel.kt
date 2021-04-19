package com.example.itunes_search.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunes_search.model.SearchModel
import com.example.itunes_search.network.Api
import com.example.itunes_search.network.Network
import com.example.itunes_search.utils.SharedPreferenceUtil
import com.example.itunes_search.utils.Status

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel : ViewModel() {

    private var _search = MutableLiveData<SearchModel>()
    val search: LiveData<SearchModel>
        get() = _search

    private var _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )

    fun getSearch(query : String, mediaType : String, context: Context){
        coroutineScope.launch {

            _status.value = Status.LOADING
            if (Network.isOnline(context)){
                var getSearch = Api.retrofitService.getSearch(query,mediaType)
                try {
                    var results = getSearch.await()
                    if (results.results.isNotEmpty()){
                        _search.value =  results
                        SharedPreferenceUtil.saveData(context,results)
                        _status.value = Status.DONE
                    }else{
                        _status.value = Status.ERROR
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _status.value = Status.ERROR
                }
            }else {
                _status.value = Status.NETWORK
            }

        }
    }

    fun getLocalData(context: Context){
        try {
            SharedPreferenceUtil.getData(context)?.let {
                _search.value=it
                _status.value = Status.DONE
            }?: kotlin.run {
                _status.value = Status.ERROR
            }

        }catch (e:Exception){
            e.printStackTrace()
            _status.value = Status.ERROR
        }


    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}