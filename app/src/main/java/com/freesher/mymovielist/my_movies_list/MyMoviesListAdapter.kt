package com.freesher.mymovielist.my_movies_list

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.freesher.mymovielist.R
import com.freesher.mymovielist.data.Movie
import kotlinx.android.synthetic.main.list_row.view.*


class MyMoviesListAdapter : RecyclerView.Adapter<MyMoviesListAdapter.MovieViewHolder>() {
    private val movieList = mutableListOf<Movie>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bindValues(movie.title!!,movie.year!!)
        holder.setClickListener(movie.id!!)
    }

    fun setMovies(newMovies:List<Movie>){
        movieList.clear()
        movieList.addAll(newMovies)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val view:View) : RecyclerView.ViewHolder(view){
        private val movieTitle = view.movieTitle
        private val movieYear = view.movieYear
        fun bindValues(title:String,year:Int){
            movieTitle.text = title
            movieYear.text = year.toString()
        }
        fun setClickListener(id:String){
            view.setOnClickListener {
                Log.e("Myapp","Click")
                val transition = MyMoviesListDirections.fromMyMoviesToMovieDetails(id)
                Navigation.createNavigateOnClickListener(transition).onClick(view)

            }
        }
    }
}
