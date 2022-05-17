package id.hikmah.binar.challenge6.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE username = :query")
    fun getUser(query: String): UserEntity

    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE username = :username AND password = :password)")
    fun checkLogin(username: String, password: String): Boolean

    @Query("SELECT id FROM UserEntity WHERE username = :username")
    fun getId(username: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Long

    @Update
    fun updateUser(user: UserEntity): Int

    @Delete
    fun deleteUser(user: UserEntity): Int
}