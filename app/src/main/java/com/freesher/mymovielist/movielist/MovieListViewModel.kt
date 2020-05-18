package com.freesher.mymovielist.movielist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.utils.NODE_MOVIES
import com.google.firebase.database.*

class MovieListViewModel : ViewModel() {
    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    val movieList: LiveData<List<Movie>>
        get() = _movieList


    fun getMoviesFromQuery(query: String) {
        Log.e("MyApp", "Run fetching")
        var fetchedMovieList: Query? = null
        if (query.isNotEmpty()) {
            fetchedMovieList = firebaseDatabase.getReference(NODE_MOVIES)
                .orderByChild("title")
                .startAt(query)

        } else {
         fetchedMovieList = firebaseDatabase.getReference(NODE_MOVIES)

    }
        Log.e("MyApp", "Continue fetching")
        val fetchedMovies = mutableListOf<Movie>()
        fetchedMovieList?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {
                TODO("not implemented")
                Log.e("Myapp", "Canceled")
                Log.e("Myapp", snapshot.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("MyApp", "Get data")
                if (snapshot.exists()) {
                    for (movieSnapshot in snapshot.children) {
                        val movie = movieSnapshot.getValue(Movie::class.java)
                        movie?.id = movieSnapshot.key
                        movie?.let { fetchedMovies.add(it) }
                    }
                    _movieList.value = fetchedMovies
                    Log.e("MyApp", "Updated values")
                }
            }

        })
    }

}