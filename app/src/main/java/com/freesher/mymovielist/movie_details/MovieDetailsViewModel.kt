package com.freesher.mymovielist.movie_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.utils.NODE_MOVIES
import com.freesher.mymovielist.utils.NODE_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MovieDetailsViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() = _movieDetails
    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite:LiveData<Boolean>
    get() = _isFavorite
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

    fun addToFavorites(movie:Movie){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userListDatabase = firebaseDatabase.getReference("/${NODE_USERS}/${userId}")
        userListDatabase.child(movie.id!!).setValue(movie)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _isFavorite.value = true
                }else{
                    _isFavorite.value  =false
                }
            }

    }
    fun removeFromFavorites(){

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userListDatabase = firebaseDatabase.getReference("/${NODE_USERS}/${userId}")
        userListDatabase.child(movieDetails.value!!.id!!).setValue(null)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _isFavorite.value = false
                }else{
                    _isFavorite.value = true
                }
            }

    }
}