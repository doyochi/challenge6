package id.hikmah.binar.challenge6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import id.hikmah.binar.challenge6.viewmodel.DataStoreViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}