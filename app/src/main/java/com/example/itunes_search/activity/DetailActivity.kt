package com.example.itunes_search.activity

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.itunes_search.R
import com.example.itunes_search.adapters.SongPreviewAdapter
import com.example.itunes_search.model.ResultFilterSongs
import com.example.itunes_search.model.ResultModel
import com.example.itunes_search.network.Network
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.search
import kotlinx.android.synthetic.main.activity_detail.songRV


class DetailActivity : AppCompatActivity() {

    private lateinit var songPreviewAdapter: SongPreviewAdapter
    private lateinit var player : MediaPlayer
    private val handler: Handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        getExtras()
        searchListener(this)
        onClickListener()
    }

    private fun getExtras() {
        try {
            intent.getParcelableExtra<ResultModel>("result")?.let {
                Glide.with(this).load(it.artworkUrl100).placeholder(R.drawable.ic_baseline_image).into(artworkUrl100);
                trackName.text = it.trackName
                artistName.text = it.artistName
                collectionName.text = it.collectionName
                collectionName_title.text = it.collectionName
                collectionPrice.text = "$ ${it.collectionPrice}"
                trackPrice.text= "$ ${it.trackPrice}"
                if (Network.isOnline(this))
                preparePlayer(it.previewUrl)
            }
            intent.getParcelableExtra<ResultFilterSongs>("songs")?.let {
                songPreviewAdapter.setData(it.listSongs as ArrayList)

            }
        } catch (e: Exception) {

        }
    }

    private fun preparePlayer(url: String){
        try {
            player = MediaPlayer()
            player.setDataSource(url)
            player.prepare()
        }catch (e:java.lang.Exception){

        }
    }

    private fun updateBar(){
        if (this::player.isInitialized && player.isPlaying){
            var progress = (player.currentPosition.toFloat()/player.duration.toFloat())*100
            updateBar.progress = progress.toInt()
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun initView() {
        songPreviewAdapter = SongPreviewAdapter()
        songRV.layoutManager = LinearLayoutManager(this)
        songRV.adapter = songPreviewAdapter
        updateBar.max = 100
        if (!Network.isOnline(this))
            playerContainer.visibility = GONE
    }

    private fun onClickListener(){
        play.setOnClickListener {
            if (this::player.isInitialized && player.isPlaying){
                handler.removeCallbacks(runnable)
                player.pause()
                play.setImageResource(R.drawable.ic_baseline_play)
            }else{
                player.start()
                play.setImageResource(R.drawable.ic_baseline_pause_circle)
                updateBar()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (this::player.isInitialized && player.isPlaying){
            handler.removeCallbacks(runnable)
            player.pause()
            play.setImageResource(R.drawable.ic_baseline_play)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::player.isInitialized && player.isPlaying){
            handler.removeCallbacks(runnable)
            player.stop()
            player.release()
        }
    }

   private val runnable = Runnable {
        updateBar()
    }

    private fun searchListener(activity: Activity) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val intent : Intent = Intent(activity, MainActivity::class.java).putExtra("query",query ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                activity.startActivity(intent)
                activity.finish()
                return false
            }

        })
    }

}