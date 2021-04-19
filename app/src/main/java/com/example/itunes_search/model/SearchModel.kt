package com.example.itunes_search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchModel(
        val resultCount: Int,
        val results: List<ResultModel>
) : Parcelable