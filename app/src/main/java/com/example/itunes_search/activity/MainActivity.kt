package com.example.itunes_search.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.itunes_search.R
import com.example.itunes_search.utils.Status
import com.example.itunes_search.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        observeViewModel()
        searchItunes("drake", "music")

    }

    private fun initViewModel(){
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private fun observeViewModel(){
        searchViewModel.search.observe(this, Observer {
            Log.e("DATA", it.results[0].artistName)
        })

        searchViewModel.status.observe(this, Observer {
            when(it){
                Status.DONE->{

                }
                Status.LOADING->{


                }
                Status.ERROR->{

                }

            }
        })
    }

    private fun searchItunes(query : String, mediaType: String){
        searchViewModel.let {
            it.getSearch(query,mediaType)
        }
    }
}