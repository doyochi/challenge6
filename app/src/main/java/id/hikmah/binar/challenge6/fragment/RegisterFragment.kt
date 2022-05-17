package id.hikmah.binar.challenge6.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.database.UserApplication
import id.hikmah.binar.challenge6.databinding.FragmentLoginBinding
import id.hikmah.binar.challenge6.databinding.FragmentRegisterBinding
import id.hikmah.binar.challenge6.model.UserViewModel
import id.hikmah.binar.challenge6.model.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            (activity?.application as UserApplication).database.userDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionRegister()

    }

    private fun actionRegister() {
        binding.btnDaftar.setOnClickListener{
            val etUsername = binding.editUsername.text.toString()
            val etEmail = binding.editEmail.text.toString()
            val etPassworda = binding.editPassword1.text.toString()
            val etPasswordb = binding.editPassword2.text.toString()

            if (validateRegisterInput(etUsername, etEmail, etPassworda, etPasswordb)) {
                lifecycleScope.launch(Dispatchers.IO){
                    inputRegister(etUsername, etEmail, etPassworda)
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "MASOK", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }
            }
        }

    }

    private fun inputRegister(username: String, email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.registerUser(username,email,password)
        }
    }

    private fun validateRegisterInput(username: String, email: String, passworda: String, passwordb: String): Boolean {

        if (username.isEmpty()) {
            binding.editUsername.error = "Username harus diisi"
            return false
        }

        if (email.isEmpty()) {
            binding.editEmail.error = "Email harus diisi"
            return false
        }

        if (passworda.isEmpty()) {
            binding.editPassword1.error = "Password harus diisi"
            return false
        }

        if (passwordb.isEmpty()) {
            binding.editPassword2.error = "Silahkan konfirmasi password"
            return false
        }

        if (passworda != passwordb) {
            binding.editPassword1.error = "Password yang Anda masukkan tidak cocok"
            return false
        }
        return true
    }


}