package id.hikmah.binar.challenge6.service

import id.hikmah.binar.challenge6.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TMDBClient {

    private const val BASE_URL = BuildConfig.BASE_URL_TMDB


    /**
     * untuk interceptor di level body
     */
    private val logging : HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    // Crate client untuk retrofit
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // create instance ApiService pakai lazy supaya sekali bikin dan seterusnya bakal manggil dari memory (yang udah pernah di bikin)
    val instance : TMDBApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofit.create(TMDBApiService::class.java)
    }
}
