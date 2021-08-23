package com.example.cloudinteractivedarrenhsieh.data

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("thumbnailUrl") var thumbnailUrl: String
)
