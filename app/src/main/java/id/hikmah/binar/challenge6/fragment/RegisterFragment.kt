package id.hikmah.binar.challenge6.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.database.UserEntity
import id.hikmah.binar.challenge6.databinding.FragmentRegisterBinding
import id.hikmah.binar.challenge6.viewmodel.RegisterViewModel
import id.hikmah.binar.challenge6.repo.UserRepo
import id.hikmah.binar.challenge6.viewModelsFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val registerViewModel: RegisterViewModel by viewModelsFactory { RegisterViewModel(userRepo) }

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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        actionRegister()

    }

    private fun actionRegister() {
        binding.btnDaftar.setOnClickListener{
            val etUsername = binding.editUsername.text.toString()
            val etEmail = binding.editEmail.text.toString()
            val etPassworda = binding.editPassword1.text.toString()
            val etPasswordb = binding.editPassword2.text.toString()

            if (validateRegisterInput(etUsername, etEmail, etPassworda, etPasswordb)) {
                val user = UserEntity(null, etUsername,etEmail,etPassworda)
                registerViewModel.addUserToDb(etUsername,etEmail,user)
            }
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

    private fun observeData() {
        registerViewModel.userIsRegist.observe(viewLifecycleOwner) {
            binding.editUsername.error = "Username sudah dipakai"
        }

        registerViewModel.emailIsRegist.observe(viewLifecycleOwner) {
            binding.editEmail.error = "Email sudah dipakai"
        }

        registerViewModel.isRegist.observe(viewLifecycleOwner) {
            if (it == false) {
                Toast.makeText(requireContext(), "Gagal Daftar", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Berhasil Daftar", Toast.LENGTH_SHORT).show()
//                binding.editUsername.error = false
//                binding.editEmail.error = false
            }
        }
    }
}