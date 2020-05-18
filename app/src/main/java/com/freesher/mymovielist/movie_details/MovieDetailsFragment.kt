package com.freesher.mymovielist.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.freesher.mymovielist.R
import com.freesher.mymovielist.data.Movie
import kotlinx.android.synthetic.main.fragment_movie_details.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    private val viewModel:MovieDetailsViewModel by viewModels()
    val args: MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                movieTitleContent.text = it.title
                movieShortDescriptionContent.text = it.shortDescription
                movieYearContent.text = it.year.toString()
                progressBar2.visibility = View.GONE
                movieDetails.visibility = View.VISIBLE
            }
        })

        val id = args.movieId
        viewModel.getDetailsAboutMovieById(id)
        progressBar2.visibility = View.VISIBLE

    }

}
