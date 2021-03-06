package id.hikmah.binar.challenge6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.challenge6.repo.UserRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepo): ViewModel() {

    val statusLogin = MutableLiveData<Boolean>()
    val username = MutableLiveData<String>()
    val userid = MutableLiveData<Int>()
    val useremail = MutableLiveData<String>()
    val pass = MutableLiveData<String>()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val checkUser = userRepo.isLogin(email, password)

            if (!checkUser.isNullOrEmpty()) {

                val getUser = userRepo.getUsernameByMail(email)
                username.value = getUser.username
                userid.value = getUser.id!!
                useremail.value = getUser.email
                pass.value = getUser.password
                // Login State = True
                statusLogin.value = true
            } else {
                statusLogin.value = false
            }
        }
    }

}