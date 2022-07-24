package id.hikmah.binar.challenge6.repo

import id.hikmah.binar.challenge6.adapter.TMDBAdapter
import id.hikmah.binar.challenge6.service.TMDBApiService

class MovieRepo(private val tmdbApiService: TMDBApiService) {
    suspend fun getMovie(apiKey: String) = tmdbApiService.getAllMovie(apiKey)
    suspend fun getMovieDetail(movieId: Int, apiKey: String) = tmdbApiService.getDetailMovie(movieId, apiKey)
}