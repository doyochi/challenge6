package id.hikmah.binar.challenge6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge6.database.UserEntity
import id.hikmah.binar.challenge6.repo.UserRepo
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepo: UserRepo): ViewModel() {

    val isRegist = MutableLiveData<Boolean>()
    val emailIsRegist = MutableLiveData<Boolean>()
    val userIsRegist = MutableLiveData<Boolean>()

    fun addUserToDb(username: String, email: String, user: UserEntity) {
        var result1 = false
        var result2 = false

        viewModelScope.launch {

            // Query check username & email
            val checkUsername = userRepo.checkRegisteredkUsername(username)
            val checkEmail = userRepo.checkRegisteredEmail(email)

            if (!checkUsername.isNullOrEmpty()) { // jika ditemukan username sudah dipakai
                userIsRegist.value = true
            } else {
                result1 = true
            }

            if (!checkEmail.isNullOrEmpty()) { // jika ditemukan email sudah dipakai
                emailIsRegist.value = true
            } else {
                result2 = true
            }

            if (result1 && result2) { // Jika username & email tersedia
                isRegist.value = true
                // Jalankan query insert to db
                userRepo.insertUser(user)
            } else {
                isRegist.value = false
            }
        }
    }

//    private fun addUser(data: UserEntity){
//        viewModelScope.launch(Dispatchers.IO) {
//            userDao.insertUser(data)
//        }
//    }
//
//    //ambil data dari fragment register
//    fun registerUser(username: String, email: String, password: String){
//        viewModelScope.launch(Dispatchers.IO) {
//            val data = data(username,email,password)
//            addUser(data) }
//    }
//
//    //ini data buat masukkan ke entity
//    private fun data(username: String, email: String, password: String): UserEntity{
//        val data = UserEntity(id = null, username, email, password)
//        return data
//    }
//
//    fun loginUser(username: String, password: String){
//        viewModelScope.launch(Dispatchers.IO){
//            userDao.checkLogin(username, password)
//        }
//    }
}

//class UserViewModelFactory(
//    private val userDao: UserDao
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return UserViewModel(userDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}