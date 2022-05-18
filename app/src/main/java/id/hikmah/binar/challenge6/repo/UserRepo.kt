package id.hikmah.binar.challenge6.repo

import android.content.Context
import id.hikmah.binar.challenge6.database.UserDatabase
import id.hikmah.binar.challenge6.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepo(context: Context) {

    private val userDB = UserDatabase.getInstance(context)

    suspend fun checkRegisteredkUsername(username: String) = withContext(Dispatchers.IO) {
        userDB?.userDao()?.checkUsername(username)
    }

    suspend fun checkRegisteredEmail(email: String) = withContext(Dispatchers.IO) {
        userDB?.userDao()?.checkEmail(email)
    }

    suspend fun checkLogin(email: String, password: String) = withContext(Dispatchers.IO) {
        userDB?.userDao()?.checkLogin(email, password)
    }

    suspend fun getDataUser(email: String) = withContext(Dispatchers.IO) {
        userDB?.userDao()?.getDataUser(email)
    }

//    suspend fun getUserDetail(username: String) = withContext(Dispatchers.IO) {
//        userDB?.userDao()?.getAllUserDetail(username)
//    }

//    suspend fun getAUser(username: String) = withContext(Dispatchers.IO) {
//        userDB?.userDao()?.getAUserDetail(username)
//    }
//
//    suspend fun insertUserDetail(userDetail: UserDetail) = withContext(Dispatchers.IO) {
//        userDB?.userDao()?.insertUserDetail(userDetail)
//    }
//
//    suspend fun updateUserProfile(username: String, nama_lengkap: String, tgl_lahir: String, alamat: String) = withContext(
//        Dispatchers.IO) {
//        userDB?.userDao()?.updateUserDetail(username, nama_lengkap, tgl_lahir, alamat)
//    }

    suspend fun insertUser(user: UserEntity) = withContext(Dispatchers.IO) {
        userDB?.userDao()?.insertUser(user)
    }

}