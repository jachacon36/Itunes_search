package com.example.itunes_search.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itunes_search.R
import com.example.itunes_search.`interface`.OpenSongDetail
import com.example.itunes_search.adapters.SearchSongAdapter
import com.example.itunes_search.utils.Status
import com.example.itunes_search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OpenSongDetail {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchSongAdapter: SearchSongAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initViewModel()
        observeViewModel()
        searchItunes("drake", "music")

    }

    private fun initViewModel(){
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private fun observeViewModel(){
        searchViewModel.search.observe(this, Observer {
            searchSongAdapter.setData(it.results as ArrayList)
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

    private fun initView(){
        searchSongAdapter = SearchSongAdapter(this)
        songRV.layoutManager = LinearLayoutManager(this)
        songRV.adapter = searchSongAdapter
    }

    private fun searchItunes(query : String, mediaType: String){
        searchViewModel.let {
            it.getSearch(query,mediaType)
        }
    }



    override fun openDetailActivity() {
        TODO("Not yet implemented")
    }
}