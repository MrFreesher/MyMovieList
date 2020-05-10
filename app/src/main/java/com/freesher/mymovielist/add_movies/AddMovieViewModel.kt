package com.freesher.mymovielist.add_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.utils.NODE_MOVIES
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class AddMovieViewModel():ViewModel(){
    private val _result = MutableLiveData<Exception?>()

    val result:LiveData<Exception?>
        get() = _result

    fun addMovie(movie: Movie){
        val dbMovies = FirebaseDatabase.getInstance().getReference(NODE_MOVIES)
        movie.id = dbMovies.push().key
        dbMovies.child(movie.id!!).setValue(movie)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value  = it.exception
                }
            }
    }
}
