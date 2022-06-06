package id.hikmah.binar.challenge6.repo

import android.content.Context
import id.hikmah.binar.challenge6.database.ProfilEntity
import id.hikmah.binar.challenge6.database.UserDao
import id.hikmah.binar.challenge6.database.UserDatabase
import id.hikmah.binar.challenge6.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {

    suspend fun checkRegisteredkUsername(username: String): List<UserEntity> {
        return userDao.checkRegisteredUsername(username)
    }

    suspend fun checkRegisteredEmail(email: String): List<UserEntity>{
        return userDao.checkRegisteredEmail(email)
    }

    suspend fun isLogin(email: String, password: String): List<UserEntity>{
        return userDao.isLogin(email, password)
    }
//
    suspend fun getUsernameByMail(email: String): UserEntity{
        return userDao.getUsernameByEmail(email)
    }
//
    suspend fun getUserDetail(username: String): List<ProfilEntity> {
        return userDao.getAllUserDetail(username)
    }
//
    suspend fun getAUser(username: String): ProfilEntity {
        return userDao.getAUserDetail(username)
    }
//
    suspend fun insertUserDetail(profilEntity: ProfilEntity){
        userDao.insertUserDetail(profilEntity)
    }

    suspend fun updateUserProfile(username: String, nama_lengkap: String, tgl_lahir: String, alamat: String) = withContext(
        Dispatchers.IO) {
        userDao.updateUserDetail(username, nama_lengkap, tgl_lahir, alamat)
    }
//
    suspend fun insertUser(user: UserEntity){
        userDao.insertUser(user)
    }

}