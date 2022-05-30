package id.hikmah.binar.challenge6.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.hikmah.binar.challenge6.BuildConfig
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.adapter.TMDBAdapter
import id.hikmah.binar.challenge6.databinding.FragmentHomeBinding
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import id.hikmah.binar.challenge6.repo.MovieRepo
import id.hikmah.binar.challenge6.service.TMDBApiService
import id.hikmah.binar.challenge6.service.TMDBClient
import id.hikmah.binar.challenge6.model.Result
import id.hikmah.binar.challenge6.model.Status
import id.hikmah.binar.challenge6.viewmodel.DataStoreViewModel
import id.hikmah.binar.challenge6.viewmodel.MovieViewModel
import id.hikmah.binar.challenge6.viewModelsFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var tmdbAdapter: TMDBAdapter

    private val pref: DataStoreRepo by lazy { DataStoreRepo(requireContext()) }
    private val dataStoreViewModel: DataStoreViewModel by viewModelsFactory{ DataStoreViewModel(pref) }

    private val tmdbApiService: TMDBApiService by lazy { TMDBClient.instance }
    private val movieRepo: MovieRepo by lazy { MovieRepo(tmdbApiService) }
    private val movieViewModel: MovieViewModel by viewModelsFactory { MovieViewModel(movieRepo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        moveToProfile()
        showUsername()
        observeMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        tmdbAdapter = TMDBAdapter { id_movies,movies: Result ->
            val bundle = Bundle()
            bundle.putInt("id _film", id_movies)
            findNavController().navigate(R.id.action_homeFragment_to_profilFragment, bundle)
        }
        binding.apply {
            rvData.adapter = tmdbAdapter
            rvData.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun moveToProfile() {
        binding.btnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profilFragment)
        }
    }

    private fun showUsername() {
        dataStoreViewModel.getUsername().observe(viewLifecycleOwner) {
            binding.txtWelcomeUser.text = "Welcome, $it"
        }
    }

    private fun observeMovie() {
        movieViewModel.getAllMoviePopular(BuildConfig.API_KEY).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    // Handle ketika data loading
                    // progress bar muncul
                    binding.pb.isVisible = true
                }
                Status.SUCCESS -> {
                    // Handle ketika data success
                    // progress bar ilang
                    binding.pb.isVisible = false
                    tmdbAdapter.updateDataRecycler(it.data)
                }
                Status.ERROR -> {
                    // Handle ketika data error
                    // progress bar ilang
                    binding.pb.isVisible = false
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}