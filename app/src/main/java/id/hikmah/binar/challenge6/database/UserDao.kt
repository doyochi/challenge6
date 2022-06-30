package id.hikmah.binar.challenge6.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE username = :query")
    fun getUser(query: String): UserEntity

    @Query("SELECT * FROM UserEntity WHERE username = :username")
    fun checkRegisteredUsername(username: String): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE email = :email")
    fun checkRegisteredEmail(email: String): List<UserEntity>

    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE username = :username AND password = :password)")
    fun checkLogin(username: String, password: String): Boolean

    //
    @Query("SELECT * FROM UserEntity WHERE email = :email AND password = :password")
    fun isLogin(email: String, password: String): List<UserEntity>

    @Query("SELECT id FROM UserEntity WHERE username = :username")
    fun getId(username: String): Int?

    // Mendapatkan username berdasarkan email login
    @Query("SELECT * FROM UserEntity WHERE email = :email")
    fun getUsernameByEmail(email: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Long

    // Mendapatkan UserDetail
    @Query("SELECT * FROM ProfilEntity WHERE username = :username")
    fun getAllUserDetail(username: String): List<ProfilEntity>

    // Mendapatkan salah satu UserDetail
    @Query("SELECT * FROM ProfilEntity WHERE username = :username")
    fun getAUserDetail(username: String): ProfilEntity

    // Insert UserDetail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetail(userDetail: ProfilEntity): Long

    // Update userdetail yang sudah ada berdasarkan ...
    @Query("UPDATE ProfilEntity SET nama_lengkap = :nama_lengkap, tgl_lahir = :tgl_lahir, alamat = :alamat WHERE username = :username")
    fun updateUserDetail(username: String, nama_lengkap: String, tgl_lahir: String, alamat: String): Int


    @Update
    fun updateUser(user: UserEntity): Int

    @Delete
    fun deleteUser(user: UserEntity): Int
}