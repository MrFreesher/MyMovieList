package com.freesher.mymovielist.movie_details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.freesher.mymovielist.HomeActivity
import com.freesher.mymovielist.R
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.movie_list.MovieListFragmentDirections
import com.google.android.material.internal.NavigationMenu
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
       setHasOptionsMenu(true)
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
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            activity?.invalidateOptionsMenu()
        })
        val id = args.movieId
        viewModel.getDetailsAboutMovieById(id)
        progressBar2.visibility = View.VISIBLE

    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if(viewModel.isFavorite.value == false){
            menu.findItem(R.id.favorite).isVisible = false
            menu.findItem(R.id.nonfavorite).isVisible = true

        }else{
            menu.findItem(R.id.favorite).isVisible = true
            menu.findItem(R.id.nonfavorite).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> { viewModel.removeFromFavorites()}
            R.id.nonfavorite -> {
                val movie = Movie().apply {
                    id = viewModel.movieDetails.value?.id
                    title = viewModel.movieDetails.value?.title
                    shortDescription = viewModel.movieDetails.value?.shortDescription
                    year = viewModel.movieDetails.value?.year

                }






                viewModel.addToFavorites(movie)}
            android.R.id.home -> {Navigation.findNavController(activity as HomeActivity,R.id.fragment).popBackStack()}

        }

        return true
    }





}
