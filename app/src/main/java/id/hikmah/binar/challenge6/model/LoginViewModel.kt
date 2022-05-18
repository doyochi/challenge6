package id.hikmah.binar.challenge6.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge6.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepo): ViewModel() {

    val statusLogin = MutableLiveData<Boolean>()
    val username = MutableLiveData<String>()
    val userid = MutableLiveData<Int>()
    val useremail = MutableLiveData<String>()
    val pass = MutableLiveData<String>()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val checkUser = userRepo.checkLogin(email, password)

            // Jika email dan password ditemukan pada db
            if (!checkUser.isNullOrEmpty()) {
                // Mendapatkan data user dari inputan login
                val getUser = userRepo.getDataUser(email)
                username.value = getUser?.username
                userid.value = getUser?.id!!
                useremail.value = getUser.email!!
                pass.value = getUser.password!!
                // Login State = True
                statusLogin.value = true
            } else {
                statusLogin.value = false
            }
        }
    }
}