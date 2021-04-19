package com.example.itunes_search.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itunes_search.R
import com.example.itunes_search.`interface`.OpenSongDetail
import com.example.itunes_search.model.ResultModel
import kotlinx.android.synthetic.main.item_song.view.*


class SearchSongAdapter(val openSongDetail: OpenSongDetail) : RecyclerView.Adapter<SearchSongAdapter.ViewHolder>() {

    var listResult: ArrayList<ResultModel> = arrayListOf()

    fun setData(listResult: ArrayList<ResultModel>) {
        this.listResult = listResult;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = listResult.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listResult[position])
        holder.itemView.container.setOnClickListener {
            openSongDetail.openDetailActivity(listResult[position], listResult)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bindItems(result: ResultModel) {
            Glide.with(itemView.context).load(result.artworkUrl60).placeholder(R.drawable.ic_baseline_image).into(itemView.artworkUrl60);
            itemView.trackName.text = result.trackName
            itemView.artistName.text = result.artistName
            itemView.collectionName.text = result.collectionName



        }
    }
}