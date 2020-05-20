package com.freesher.mymovielist.add_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.freesher.mymovielist.R
import com.freesher.mymovielist.data.Movie
import com.freesher.mymovielist.utils.toast
import kotlinx.android.synthetic.main.fragment_add_movies.*
import java.util.*


class AddMoviesFragment : Fragment() {
    private val viewModel: AddMovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null){
                requireContext().toast("The movie was added")
            }else{
                requireContext().toast(it!!.message!!)
            }
        })
        addMoviesButton.setOnClickListener {
            val title = movieTitle.text.toString().trim()
            val year = movieYear.text.toString().trim()
            val shortDescription = movieShortDescription.text.toString().trim()
            val director = movieDirector.text.toString().trim()
            val isTitleNotEmpty = checkIsTitleNotEmpty(title)
            val isYearValid = validateYear(year)
            val isShortDescriptionNotEmpty = checkIsShortDescriptionNotEmpty(shortDescription)
            val isDirectorNotEmpty = checkIsDirectorNotEmpty(director)

            if (isTitleNotEmpty && isYearValid && isShortDescriptionNotEmpty && isDirectorNotEmpty) {
               val movie = Movie()
                movie.title = title
                movie.shortDescription = shortDescription
                movie.year = year.toInt()
                movie.director = director
                viewModel.addMovie(movie)




            }
        }
    }


    private fun checkIsTitleNotEmpty(title: String): Boolean {
        if (title.isEmpty()) {
            movieTitle.error = "Empty title"
            return false
        } else {
            return true
        }
    }

    private fun checkIsYearNotEmpty(year: String): Boolean {
        if (year.isEmpty()) {
            movieYear.error = "Empty year"
            return false

        } else {
            return true
        }
    }

    private fun checkIsShortDescriptionNotEmpty(shortDescription: String): Boolean {
        if (shortDescription.isEmpty()) {
            movieShortDescription.error = "Empty short description"
            return false
        } else {
            return true
        }
    }

    private fun checkIsDirectorNotEmpty(director:String):Boolean{
        if(director.isEmpty()){
            movieDirector.error = "Empty director"
            return false
        }else{
            return true
        }
    }

    private fun validateYear(year: String): Boolean {
        if (checkIsYearNotEmpty(year)) {
            val actualYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year.length != 4) {
                movieYear.error = "Year has length of 4"
                return false
            } else {
                if (year.toInt() <= actualYear) {
                    return true
                } else {
                    movieYear.error = "Year must be lower or equal actual year"
                    return false
                }
                return true
            }
        } else {
            return false
        }
    }

}
