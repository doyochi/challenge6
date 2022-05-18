package id.hikmah.binar.challenge6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.hikmah.binar.challenge6.databinding.ActivitySplashScreenBinding
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import id.hikmah.binar.challenge6.viewmodel.DataStoreViewModel

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var handler: Handler

    private val pref: DataStoreRepo by lazy { DataStoreRepo(this) }
    private val dataStoreViewModel: DataStoreViewModel by viewModelsFactory { DataStoreViewModel(pref) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLoginState()
    }

    private fun checkLoginState() {
        dataStoreViewModel.getLoginState().observe(this) {
            if (it == true) {
                handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 3000)
            } else {
                handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    startActivity(Intent(this, LoginRegActivity::class.java))
                    finish()
                }, 3000) // 3 detik
            }
        }
    }
}