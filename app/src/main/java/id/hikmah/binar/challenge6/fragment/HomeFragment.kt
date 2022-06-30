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

    private val pref: DataStoreRepo by lazy { DataStoreRepo(requireContext()) }
    private val dataStoreViewModel: DataStoreViewModel by viewModelsFactory{ DataStoreViewModel(pref) }

    private lateinit var tmdbAdapter: TMDBAdapter
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

        showUsername()
        initRecyclerView()
        observeMovie()
        moveToProfil()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showUsername() {
        dataStoreViewModel.getUsername().observe(viewLifecycleOwner) {
            binding.txtWelcomeUser.text = "Welcome, $it"
        }
    }

    private fun initRecyclerView() {
        tmdbAdapter = TMDBAdapter { id_mov,pilem: Result ->
            val bundle = Bundle()
            bundle.putInt("movi_id", id_mov)
            findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
        }
        binding.apply {
            rvData.adapter = tmdbAdapter
            rvData.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeMovie() {
        movieViewModel.getAllMoviePopular(BuildConfig.API_KEY).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> { binding.pb.isVisible = true }
                Status.SUCCESS -> {
                    binding.pb.isVisible = false
                    tmdbAdapter.updateDataRecycler(it.data)
                }
                Status.ERROR -> {
                    binding.pb.isVisible = false
                    Toast.makeText(requireContext(),
                        "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun moveToProfil() {
        binding.btnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profilFragment)
        }
    }
}