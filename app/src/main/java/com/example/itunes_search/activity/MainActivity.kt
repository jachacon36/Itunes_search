package com.example.itunes_search.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.itunes_search.R
import com.example.itunes_search.`interface`.OpenSongDetail
import com.example.itunes_search.adapters.SearchSongAdapter
import com.example.itunes_search.model.ResultFilterSongs
import com.example.itunes_search.model.ResultModel
import com.example.itunes_search.utils.Status
import com.example.itunes_search.viewmodel.SearchViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.search
import kotlinx.android.synthetic.main.activity_main.songRV

class MainActivity : AppCompatActivity(), OpenSongDetail {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchSongAdapter: SearchSongAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initViewModel()
        observeViewModel()
        searchListener()
        searchItunes("drake", "music")
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private fun observeViewModel() {
        searchViewModel.search.observe(this, Observer {
            searchSongAdapter.setData(it.results as ArrayList)
        })

        searchViewModel.status.observe(this, Observer {
            when (it) {
                Status.DONE -> {
                    loading.visibility = GONE
                    songRV.visibility = VISIBLE
                    error.visibility= GONE

                }
                Status.LOADING -> {
                    loading.visibility = VISIBLE
                    songRV.visibility = GONE
                    error.visibility= GONE


                }
                Status.ERROR -> {
                    loading.visibility = GONE
                    songRV.visibility = GONE
                    error.visibility= VISIBLE

                }

            }
        })
    }

    private fun initView() {
        searchSongAdapter = SearchSongAdapter(this)
        songRV.layoutManager = LinearLayoutManager(this)
        songRV.adapter = searchSongAdapter
    }

    private fun searchItunes(query: String, mediaType: String) {
        searchViewModel.let {
            it.getSearch(query, mediaType)
        }
    }


    override fun openDetailActivity(
        result: ResultModel,
        listResult: ArrayList<ResultModel>
    ) {
        var resultFilterSongs= ResultFilterSongs(filterSongsList(result,listResult))
        val intent : Intent = Intent(this, DetailActivity::class.java).putExtra("result",result ).putExtra("songs",resultFilterSongs)
        startActivity(intent)
    }

    private fun searchListener() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.getSearch(query, "music")
                return false
            }

        })
    }

    private fun filterSongsList(result: ResultModel,
                                listResult: ArrayList<ResultModel>): ArrayList<ResultModel> {
        val listTemp : ArrayList<ResultModel> = arrayListOf()
        listResult.forEach {
            if (it.collectionName.equals(result.collectionName))
                listTemp.add(it)
             }
        return listTemp
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        getExtras(intent)
    }
    private fun getExtras(intent: Intent?) {
        try {
            intent?.getStringExtra("query")?.let {
                searchItunes(it, "music")
                search.isIconified = false
                search.setQuery(it,false)
                search.clearFocus()
            }

        } catch (e: Exception) {

        }
    }
}