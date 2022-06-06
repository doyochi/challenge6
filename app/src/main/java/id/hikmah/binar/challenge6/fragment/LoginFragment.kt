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
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import id.hikmah.binar.challenge6.MainActivity
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.databinding.FragmentLoginBinding
import id.hikmah.binar.challenge6.viewmodel.DataStoreViewModel
import id.hikmah.binar.challenge6.viewmodel.LoginViewModel
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import id.hikmah.binar.challenge6.repo.UserRepo
import id.hikmah.binar.challenge6.viewModelsFactory

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

//    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val loginViewModel: LoginViewModel by viewModels()

    private val pref: DataStoreRepo by lazy { DataStoreRepo(requireContext()) }
    private val dataStoreViewModel: DataStoreViewModel by viewModelsFactory { DataStoreViewModel(pref) }
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

        moveToRegister()
        actionLogin()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun moveToRegister() {
        binding.btnToregist.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun actionLogin() {
        binding.btnLogin.setOnClickListener {
            val username = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            if (validateLogin(username, password)) {
//                isLogin(username, password)
                loginViewModel.loginUser(username, password)
                Toast.makeText(requireContext(), "MLEBU", Toast.LENGTH_SHORT).show()
            }

        }

    }

//    private fun isLogin(username: String, password: String) {
//        viewModel.loginUser(username, password)
//    }

    private fun validateLogin(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            binding.editEmail.error = "Masukkan username Anda"
            return false
        }

        if (password.isEmpty()) {
            binding.editPassword.error = "Masukkan password Anda"
            return false
        }
        return true
    }

    private fun observeData() {
        loginViewModel.statusLogin.observe(viewLifecycleOwner) {
            if (it == false) {
                Toast.makeText(requireContext(), "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            } else {
                dataStoreViewModel.saveLoginState(it)
                Toast.makeText(requireContext(), "Berhasil Login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }

        loginViewModel.username.observe(viewLifecycleOwner) {
            dataStoreViewModel.saveUsername(it)
        }

    }
}