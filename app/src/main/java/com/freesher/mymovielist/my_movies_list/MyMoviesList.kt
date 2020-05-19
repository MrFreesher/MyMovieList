package com.freesher.mymovielist.my_movies_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.freesher.mymovielist.R
import kotlinx.android.synthetic.main.fragment_my_movies_list.*

/**
 * A simple [Fragment] subclass.
 */
class MyMoviesList : Fragment() {
    private val viewModel:MyMoviesListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myMovieListAdapter = MyMoviesListAdapter()
        myMoviesRecyclerView.apply {
            adapter = myMovieListAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
        myMoviesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            myMovieListAdapter.setMovies(it)
            progressBar3.visibility = View.GONE
        })

        viewModel.getMoviesFromDatabase()
        progressBar3.visibility = View.VISIBLE
    }

}
