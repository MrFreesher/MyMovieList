package com.freesher.mymovielist.movie_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.utils.NODE_MOVIES
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MovieDetailsViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() = _movieDetails

    fun getDetailsAboutMovieById(id: String) {
        val moviesDatabase = firebaseDatabase.getReference("/$NODE_MOVIES/${id}")

        moviesDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val movie = snapshot.getValue(Movie::class.java)
//                    val movie = snapshot.children.iterator().next()
//                        .getValue(Movie::class.java)
                    movie?.id = snapshot.key
                    _movieDetails.value = movie
                }
            }

        })
    }
}