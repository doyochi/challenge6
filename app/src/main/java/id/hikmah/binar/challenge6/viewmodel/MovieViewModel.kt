package id.hikmah.binar.challenge6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.hikmah.binar.challenge6.model.Resource
import id.hikmah.binar.challenge6.repo.MovieRepo
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val movieRepo: MovieRepo): ViewModel() {

    fun getAllMoviePopular(apiKey: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(movieRepo.getMovie(apiKey)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error!"))
        }
    }

    fun getMovieDetail(movieId: Int, apiKey: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(movieRepo.getMovieDetail(movieId, apiKey)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error!"))
        }
    }
}