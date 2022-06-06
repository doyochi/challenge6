package id.hikmah.binar.challenge6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge6.database.ProfilEntity
import id.hikmah.binar.challenge6.repo.UserRepo
import kotlinx.coroutines.launch

class ProfilViewMode(private val userRepo: UserRepo): ViewModel() {

    val statusUpdate = MutableLiveData<Boolean>()
    val usernameDetail = MutableLiveData<String>()
    val namaLengkapDetail = MutableLiveData<String>()
    val tglLahirDetail = MutableLiveData<String>()
    val alamatDetail = MutableLiveData<String>()

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            val result = userRepo.getUserDetail(username)
            val userDetail = userRepo.getAUser(username)

            if (!result.isNullOrEmpty()) {
                // Munculkan value dari tabel UserDetail ke EditText via MutableLiveData
                usernameDetail.value = userDetail?.username!!
                namaLengkapDetail.value = userDetail.nama_lengkap!!
                tglLahirDetail.value = userDetail.tgl_lahir!!
                alamatDetail.value = userDetail.alamat!!
            } else {
                usernameDetail.value = username
            }

        }
    }

    fun updateUserDetail(username: String, namaLengkap: String, tglLahir: String, alamat: String, profilEntity: ProfilEntity) {
        viewModelScope.launch {
            val result = userRepo.getUserDetail(username)
            if (!result.isNullOrEmpty()) {
                userRepo.updateUserProfile(username, namaLengkap,tglLahir, alamat)
            } else {
                userRepo.insertUserDetail(profilEntity)
            }
            statusUpdate.value = true
        }
    }

}