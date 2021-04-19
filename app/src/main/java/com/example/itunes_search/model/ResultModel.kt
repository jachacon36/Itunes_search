package com.example.itunes_search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultModel (
    val artistName : String,
    val collectionName : String,
    val trackName: String,
    val artworkUrl30: String,
    val trackPrice : String
): Parcelable