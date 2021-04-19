package com.example.itunes_search.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes_search.R
import com.example.itunes_search.model.ResultModel
import kotlinx.android.synthetic.main.item_song_peview.view.*


class SongPreviewAdapter() : RecyclerView.Adapter<SongPreviewAdapter.ViewHolder>() {

    var listResult: ArrayList<ResultModel> = arrayListOf()

    fun setData(listResult: ArrayList<ResultModel>) {
        this.listResult = listResult;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_song_peview, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = listResult.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listResult[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bindItems(result: ResultModel) {
            itemView.trackName.text = result.trackName
            itemView.artistName.text = result.artistName
            val seconds = (result.trackTimeMillis / 1000 % 60)
            val minutes = (result.trackTimeMillis / 1000 / 60)
            itemView.trackTimeMillis.text = "${minutes}:${seconds}"



        }
    }
}