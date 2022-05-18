package id.hikmah.binar.challenge6.repo

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepo (private val context: Context) {

    suspend fun saveLoginStateToDataStore(value: Boolean) {
        context.dataStore.edit { pref ->
            pref[LOGINSTATE_KEY] = value
        }
    }

    fun readLoginStateFromDataStore(): Flow<Boolean> {
        return context.dataStore.data.map { pref ->
            pref[LOGINSTATE_KEY] ?: false
        }
    }

    suspend fun saveUsernameToDataStore(value: String) {
        context.dataStore.edit { pref ->
            pref[USERNAME_KEY] = value
        }
    }

    fun readUsernameFromDataStore(): Flow<String> {
        return context.dataStore.data.map { pref ->
            pref[USERNAME_KEY] ?: "Binar"
        }
    }

    suspend fun saveImageToDataStore(value: String) {
        context.dataStore.edit { pref ->
            pref[IMAGE_KEY] = value
        }
    }

    fun readImageFromDataStore() : Flow<String> {
        return context.dataStore.data.map { pref ->
            pref[IMAGE_KEY] ?: ""
        }
    }

    suspend fun removeFromDataStore() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "datastore_preference"
        private val LOGINSTATE_KEY = booleanPreferencesKey("loginstate_key")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val IMAGE_KEY = stringPreferencesKey("image_key")
        private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    }


}