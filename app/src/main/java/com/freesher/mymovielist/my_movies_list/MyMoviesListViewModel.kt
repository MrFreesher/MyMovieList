package com.freesher.mymovielist.my_movies_list

import android.renderscript.Sampler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.utils.NODE_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyMoviesListViewModel :ViewModel(){
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val _movieList:MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList: LiveData<List<Movie>>
    get() = _movieList

    fun getMoviesFromDatabase(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userListDatabase = firebaseDatabase.getReference("/${NODE_USERS}/${userId}")
        userListDatabase.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedMovies = mutableListOf<Movie>()
              if(snapshot.exists()){
                  for(movieSnapshot in snapshot.children){
                      val movie = movieSnapshot.getValue(Movie::class.java)
                      movie?.id = movieSnapshot.key
                      movie?.let {
                          fetchedMovies.add(it)
                      }
                  }
                  _movieList.value = fetchedMovies
              }
            }

        })
    }

}