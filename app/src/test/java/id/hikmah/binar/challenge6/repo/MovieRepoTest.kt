package id.hikmah.binar.challenge6.repo

import id.hikmah.binar.challenge6.BuildConfig
import id.hikmah.binar.challenge6.adapter.TMDBAdapter
import id.hikmah.binar.challenge6.model.MovieResponse
import id.hikmah.binar.challenge6.model.Result
import id.hikmah.binar.challenge6.service.TMDBApiService
import id.hikmah.binar.challenge6.service.TMDBClient
import id.hikmah.binar.challenge6.viewmodel.MovieViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.runner.Version.id
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test

class MovieRepoTest {
    private lateinit var service: TMDBApiService
    private lateinit var repo: MovieRepo

    @Before
    fun setUp(){
        service = mockk()
        repo = MovieRepo(service)
    }
    @Test
    fun getMovie(): Unit = runBlocking {
        val respAllMovies = mockk<MovieResponse>()

        every {
            runBlocking {
                service.getAllMovie(BuildConfig.API_KEY)
            }
        } returns respAllMovies
        repo.getMovie(BuildConfig.API_KEY)
        verify {
            runBlocking { service.getAllMovie(BuildConfig.API_KEY) }
        }
    }

}