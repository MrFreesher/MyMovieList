package com.freesher.mymovielist.add_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.freesher.mymovielist.R
import com.freesher.mymovielist.utils.toast
import kotlinx.android.synthetic.main.fragment_add_movies.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMoviesButton.setOnClickListener {
            val title = movieTitle.text.toString().trim()
            val year = movieYear.text.toString().trim()
            val shortDescription = movieShortDescription.text.toString().trim()
            val isTitleNotEmpty = checkIsTitleNotEmpty(title)
            val isYearValid = validateYear(year)
            val isShortDescriptionNotEmpty = checkIsShortDescriptionNotEmpty(shortDescription)

            if (isTitleNotEmpty && isYearValid && isShortDescriptionNotEmpty) {
                view.context.toast("Everything is valid")
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
