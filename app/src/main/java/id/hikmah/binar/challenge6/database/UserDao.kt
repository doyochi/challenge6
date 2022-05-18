package id.hikmah.binar.challenge6.database

import androidx.room.*
import id.hikmah.binar.challenge6.repo.UserProfile

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM UserEntity WHERE username = :query")
    fun getUser(query: String): UserEntity

    @Query("SELECT * FROM UserEntity WHERE username = :username")
    fun checkUsername(username: String): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE email = :email")
    fun checkEmail(email: String): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE username = :username AND password = :password")
    fun checkLogin(username: String, password: String): List<UserEntity>

    // Mendapatkan username berdasarkan email login
    @Query("SELECT * FROM UserEntity WHERE email = :email")
    fun getDataUser(email: String): UserEntity

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    fun getUsername(id: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfil(userProfile: UserProfile): Long

    @Update
    fun updateUser(user: UserEntity): Int

    @Delete
    fun deleteUser(user: UserEntity): Int
}