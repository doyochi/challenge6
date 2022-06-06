package id.hikmah.binar.challenge6.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.databinding.FragmentLoginBinding
import id.hikmah.binar.challenge6.databinding.FragmentProfilBinding
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import id.hikmah.binar.challenge6.viewModelsFactory
import id.hikmah.binar.challenge6.viewmodel.DataStoreViewModel
import id.hikmah.binar.challenge6.viewmodel.LoginViewModel

class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    //    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val loginViewModel: LoginViewModel by viewModels()

    private val pref: DataStoreRepo by lazy { DataStoreRepo(requireContext()) }
    private val dataStoreViewModel: DataStoreViewModel by viewModelsFactory { DataStoreViewModel(pref) }

    companion object {
        const val REQUEST_CODE_PERMISSION = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

}