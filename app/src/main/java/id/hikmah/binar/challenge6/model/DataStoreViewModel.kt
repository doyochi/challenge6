package id.hikmah.binar.challenge6.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge6.repo.UserPreferencesManager
import kotlinx.coroutines.launch

class DataStoreViewModel(private val pref: UserPreferencesManager): ViewModel() {

    fun setLogin(value: Boolean) {
        viewModelScope.launch {
            pref.setLoginTo(value)
        }
    }

    fun saveUser(value: String) {
        viewModelScope.launch {
            pref.setUserTo(value)
        }
    }

    fun saveImage(value: String) {
        viewModelScope.launch {
            pref.saveImageTo(value)
        }
    }

    fun getLogin() : LiveData<Boolean> {
        return pref.getLoginFrom().asLiveData()
    }

    fun getUsername() : LiveData<String> {
        return pref.getUserFrom().asLiveData()
    }

    fun getImage() : LiveData<String> {
        return pref.getImageFrom().asLiveData()
    }

    fun clearAllData() {
        viewModelScope.launch {
            pref.clearFrom()
        }
    }
}