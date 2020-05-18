package com.freesher.mymovielist.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.freesher.mymovielist.R
import com.freesher.mymovielist.utils.toast
import kotlinx.android.synthetic.main.fragment_movie_list.*

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : Fragment() {
    private val viewModel: MovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movieListAdapter = MovieListAdapter()

        movieListRecyclerView.apply {
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
        movieListRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL))

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            Log.e("MyApp","DFone")
            movieListAdapter.setMovies(it)
            progressBar.visibility = View.GONE
        })
        searchMovieBtn.setOnClickListener {
            val query = movieTitleInput.text.toString()
            viewModel.getMoviesFromQuery(query)
            progressBar.visibility = View.VISIBLE
        }


    }

}
