package id.hikmah.binar.challenge6.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.hikmah.binar.challenge6.database.UserDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun databaseProvider(
        @ApplicationContext context: Context
    ): UserDatabase = Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        "user_database.db"
    ).fallbackToDestructiveMigration()
     .build()

    @Singleton
    @Provides
    fun userDaoProvider(database: UserDatabase) = database.userDao()
}