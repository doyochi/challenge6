package id.hikmah.binar.challenge6.database

import android.app.Application

class UserApplication: Application() {
    val database by lazy { UserDatabase.getInstance(this) }
}