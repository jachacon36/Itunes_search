package com.example.itunes_search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultModel (
    val artistName : String,
    val collectionName : String,
    val trackName: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val trackTimeMillis: Int,
    val trackPrice : Double,
    val collectionPrice: Double,
    val previewUrl: String
): Parcelable