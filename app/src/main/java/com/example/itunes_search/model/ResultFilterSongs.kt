package com.example.itunes_search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultFilterSongs (
    val listSongs : List<ResultModel>
): Parcelable