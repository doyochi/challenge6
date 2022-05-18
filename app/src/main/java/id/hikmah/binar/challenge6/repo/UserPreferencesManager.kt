package id.hikmah.binar.challenge6.repo

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesManager(private val context: Context) {

    //set--to Data Store

    suspend fun setLoginTo(value: Boolean) {
        context.dataStore.edit { pref -> pref[LOGIN_KEY] = value
        }
    }

    suspend fun setUserTo(value: String) {
        context.dataStore.edit { pref -> pref[USERNAME_KEY] = value
        }
    }

    suspend fun saveImageTo(value: String) {
        context.dataStore.edit { pref -> pref[IMAGE_KEY] = value
        }
    }

    fun getLoginFrom(): Flow<Boolean> {
        return context.dataStore.data.map { pref -> pref[LOGIN_KEY] ?: false
        }
    }

    fun getUserFrom(): Flow<String> {
        return context.dataStore.data.map { pref -> pref[USERNAME_KEY] ?: "Binar"
        }
    }

    fun getImageFrom() : Flow<String> {
        return context.dataStore.data.map { pref -> pref[IMAGE_KEY] ?: ""
        }
    }

    suspend fun clearFrom() {
        context.dataStore.edit { pref -> pref.clear()
        }
    }

    companion object {
        private const val DATA_STORE_PREFERENCES = "user_preference"
        private val LOGIN_KEY = booleanPreferencesKey("login_key")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val IMAGE_KEY = stringPreferencesKey("image_key")
        private val Context.dataStore by preferencesDataStore(name = DATA_STORE_PREFERENCES)
    }

}