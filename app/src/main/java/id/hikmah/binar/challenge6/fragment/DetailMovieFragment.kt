package id.hikmah.binar.challenge6.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import id.hikmah.binar.challenge6.BuildConfig
import id.hikmah.binar.challenge6.databinding.FragmentDetailMovieBinding
import id.hikmah.binar.challenge6.model.Status
import id.hikmah.binar.challenge6.repo.MovieRepo
import id.hikmah.binar.challenge6.service.TMDBApiService
import id.hikmah.binar.challenge6.service.TMDBClient
import id.hikmah.binar.challenge6.viewModelsFactory
import id.hikmah.binar.challenge6.viewmodel.MovieViewModel

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val tmdbApiService: TMDBApiService by lazy { TMDBClient.instance }
    private val movieRepo: MovieRepo by lazy { MovieRepo(tmdbApiService) }
    private val movieViewModel: MovieViewModel by viewModelsFactory { MovieViewModel(movieRepo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getMovIdBundle = arguments?.getInt("movi_id")
        observeDetailMovie(getMovIdBundle!!)
    }

    private fun observeDetailMovie(movieId: Int) {
        movieViewModel.getMovieDetail(movieId, BuildConfig.API_KEY).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {binding.pb.isVisible = true}
                Status.SUCCESS -> {
                    binding.pb.isVisible = false
                    binding.apply {
                        Glide.with(requireContext())
                            .load(BuildConfig.BASE_URL_BACKDROP + it.data?.backdropPath)
                            .into(backgroundThumb)
                        Glide.with(requireContext())
                            .load(BuildConfig.BASE_URL_IMAGE + it.data?.posterPath)
                            .into(thumbMoviedetail)
                        txtTitleMoviedetail.text = it.data?.title
                        txtReleasedateMoviedetail.text = "Release Date: ${it.data?.releaseDate}"
                        txtOverviewMoviedetail.text = it.data?.overview
                    }
                }
                Status.ERROR -> {
                    binding.pb.isVisible = false
                    Toast.makeText(requireContext(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}