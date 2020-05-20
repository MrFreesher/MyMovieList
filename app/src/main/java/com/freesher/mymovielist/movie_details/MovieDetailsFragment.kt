package com.freesher.mymovielist.movie_details

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.freesher.mymovielist.HomeActivity
import com.freesher.mymovielist.R
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.utils.toast
import kotlinx.android.synthetic.main.fragment_movie_details.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailsViewModel by viewModels()
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
                movieDirectorContent.text = it.director
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
        viewModel.checkIsMovieInUserList(id)

    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (viewModel.isFavorite.value == false) {
            menu.findItem(R.id.favorite).isVisible = false
            menu.findItem(R.id.nonfavorite).isVisible = true

        } else {
            menu.findItem(R.id.favorite).isVisible = true
            menu.findItem(R.id.nonfavorite).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle("Remove from favorites")
                    it.setMessage("Are you sure to remove this movie from your list")
                    it.setPositiveButton(
                        "Yes"
                    ) { dialog, _ ->


                        dialog.dismiss()
                        progressBar2.visibility = View.VISIBLE
                        viewModel.removeFromFavorites()
                        progressBar2.visibility = View.GONE
                        requireContext().toast("Movie was removed from your list")
                    }
                    it.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                }.show()

            }
            R.id.nonfavorite -> {
                val movie = Movie().apply {
                    id = viewModel.movieDetails.value?.id
                    title = viewModel.movieDetails.value?.title
                    shortDescription = viewModel.movieDetails.value?.shortDescription
                    year = viewModel.movieDetails.value?.year

                }





                progressBar2.visibility = View.VISIBLE
                viewModel.addToFavorites(movie)
                progressBar2.visibility = View.GONE
                requireContext().toast("Movie was added to your list")
            }
            android.R.id.home -> {
                Navigation.findNavController(activity as HomeActivity, R.id.fragment).popBackStack()
            }

        }

        return true
    }


}
