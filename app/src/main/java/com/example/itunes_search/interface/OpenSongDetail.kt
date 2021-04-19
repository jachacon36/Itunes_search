package com.example.itunes_search.`interface`

import com.example.itunes_search.model.ResultModel

interface OpenSongDetail {
    fun openDetailActivity(
        result: ResultModel,
        listResult: ArrayList<ResultModel>
    )
}