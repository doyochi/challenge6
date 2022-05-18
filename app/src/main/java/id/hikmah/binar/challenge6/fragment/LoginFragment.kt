package id.hikmah.binar.challenge6.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge6.MainActivity
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.database.UserApplication
import id.hikmah.binar.challenge6.databinding.FragmentLoginBinding
import id.hikmah.binar.challenge6.model.DataStoreViewModel
import id.hikmah.binar.challenge6.model.LoginViewModel
import id.hikmah.binar.challenge6.model.RegisterViewModel
import id.hikmah.binar.challenge6.repo.UserPreferencesManager
import id.hikmah.binar.challenge6.repo.UserRepo
import id.hikmah.binar.challenge6.viewModelsFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val loginViewModel: LoginViewModel by viewModelsFactory { LoginViewModel(userRepo) }

    private val pref: UserPreferencesManager by lazy { UserPreferencesManager(requireContext()) }
    private val datastoreViewModel: DataStoreViewModel by viewModelsFactory { DataStoreViewModel(pref) }


//    private val viewModel: RegisterViewModel by viewModels {
//        UserViewModelFactory(
//            (activity?.application as UserApplication).database.userDao()
//        )
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionLogin()
        moveToRegister()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun actionLogin() {
        binding.btnLogin.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            if (validateLogin(username, password)) {
                observeData(username, password)
                Toast.makeText(requireContext(), "MLEBU", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun observeData(username: String, password: String) {
//        viewModel.loginUser(username, password)
        loginViewModel.statusLogin.observe(viewLifecycleOwner) {
            if (it == false) { // jika gagal
                Toast.makeText(requireContext(), "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            } else { // jika berhasil
                // Simpan Login State ke Datastore
                datastoreViewModel.setLogin(it) // True
                // Munculkan toast 'Berhasil Login'
                Toast.makeText(requireContext(), "Berhasil Login", Toast.LENGTH_SHORT).show()
                // Pindah screen ke HomeFragment (berada di MainActivity)
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }

        loginViewModel.username.observe(viewLifecycleOwner) {
            datastoreViewModel.saveUser(it)
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            binding.editUsername.error = "Masukkan username Anda"
            return false
        }

        if (password.isEmpty()) {
            binding.editPassword.error = "Masukkan password Anda"
            return false
        }
        return true
    }

    private fun moveToRegister() {
        binding.btnToregist.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}