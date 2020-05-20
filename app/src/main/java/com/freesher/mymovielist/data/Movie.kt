package com.freesher.mymovielist.data

import com.google.firebase.database.Exclude

data class Movie(
    @get:Exclude
    var id: String? = null,
    var title: String? = null,
    var year: Int? = null,
    var shortDescription: String? = null,
    var director:String? = null
)