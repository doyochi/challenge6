package id.hikmah.binar.challenge6.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import kotlinx.coroutines.launch

class DataStoreViewModel(private val pref: DataStoreRepo): ViewModel(){

    fun saveLoginState(value: Boolean) {
        viewModelScope.launch {
            pref.saveLoginStateToDataStore(value)
        }
    }

    fun getLoginState() : LiveData<Boolean> {
        return pref.readLoginStateFromDataStore().asLiveData()
    }

    fun saveUsername(value: String) {
        viewModelScope.launch {
            pref.saveUsernameToDataStore(value)
        }
    }

    fun getUsername() : LiveData<String> {
        return pref.readUsernameFromDataStore().asLiveData()
    }

    fun saveImage(value: String) {
        viewModelScope.launch {
            pref.saveImageToDataStore(value)
        }
    }

    fun getImage() : LiveData<String> {
        return pref.readImageFromDataStore().asLiveData()
    }

    fun deleteAllData() {
        viewModelScope.launch {
            pref.removeFromDataStore()
        }
    }
}