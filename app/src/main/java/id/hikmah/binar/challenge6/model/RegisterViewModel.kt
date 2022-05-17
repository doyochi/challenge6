package id.hikmah.binar.challenge6.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge6.database.UserDao
import id.hikmah.binar.challenge6.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao): ViewModel() {


    private fun addUser(data: UserEntity){
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(data)
        }
    }

    //ambil data dari fragment register
    fun registerUser(username: String, email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val data = data(username,email,password)
            addUser(data) }
    }

    //ini data buat masukkan ke entity
    private fun data(username: String, email: String, password: String): UserEntity{
        val data = UserEntity(id = null, username, email, password)
        return data
    }

    fun loginUser(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO){
            userDao.checkLogin(username, password)
        }
    }
}

class UserViewModelFactory(
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}