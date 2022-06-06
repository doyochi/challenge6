package id.hikmah.binar.challenge6.repo

import id.hikmah.binar.challenge6.service.TMDBApiService
import javax.inject.Inject

class MovieRepo @Inject constructor(private val tmdbApiService: TMDBApiService) {
    suspend fun getMovie(apiKey: String) = tmdbApiService.getAllMovie(apiKey)
    suspend fun getMovieDetail(movieId: Int, apiKey: String) = tmdbApiService.getDetailMovie(movieId, apiKey)
}