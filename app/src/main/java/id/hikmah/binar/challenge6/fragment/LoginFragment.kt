package id.hikmah.binar.challenge6.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge6.MainActivity
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.databinding.FragmentLoginBinding
import id.hikmah.binar.challenge6.viewmodel.DataStoreViewModel
import id.hikmah.binar.challenge6.viewmodel.LoginViewModel
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import id.hikmah.binar.challenge6.repo.UserRepo
import id.hikmah.binar.challenge6.viewModelsFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val loginViewModel: LoginViewModel by viewModelsFactory { LoginViewModel(userRepo) }

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

        observeData()
        actionLogin()
        moveToRegister()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeData() {
        loginViewModel.statusLogin.observe(viewLifecycleOwner) {
            if (it == false) {Toast.makeText(requireContext(),
                "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            } else {
                dataStoreViewModel.saveLoginState(it)
                Toast.makeText(requireContext(), "Berhasil Login", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        loginViewModel.username.observe(viewLifecycleOwner) {
            dataStoreViewModel.saveUsername(it)
        }

    }

    private fun moveToRegister() {
        binding.btnToregist.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun actionLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEdit.text.toString()
            val password = binding.editPassword.text.toString()
            if (validateLogin(email, password)) {
//                isLogin(username, password)
                loginViewModel.loginUser(email, password)
                Toast.makeText(requireContext(), "MLEBU", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun isLogin(username: String, password: String) {
//        viewModel.loginUser(username, password)
    }

    private fun validateLogin(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailEdit.error = "Masukkan username Anda"
            return false
        }

        if (password.isEmpty()) {
            binding.editPassword.error = "Masukkan password Anda"
            return false
        }
        return true
    }
}