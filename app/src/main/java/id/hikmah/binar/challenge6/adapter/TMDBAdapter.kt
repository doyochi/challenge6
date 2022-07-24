package id.hikmah.binar.challenge6.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.challenge6.BuildConfig
import id.hikmah.binar.challenge6.databinding.ItemDataBinding
import id.hikmah.binar.challenge6.model.MovieResponse
import id.hikmah.binar.challenge6.model.Result
import id.hikmah.binar.challenge6.service.TMDBApiService
import okhttp3.Interceptor.Companion.invoke

class TMDBAdapter(private val onClickListers: (id: Int, movie: Result) -> Unit):
    RecyclerView.Adapter<TMDBAdapter.TMDBViewHolder>(){

        val diffCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

        }

        private var differ = AsyncListDiffer(this, diffCallback)

        fun updateDataRecycler(movies: MovieResponse?) = differ.submitList(movies?.results)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TMDBViewHolder {
            val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TMDBViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TMDBViewHolder, position: Int) {
            holder.bind(differ.currentList[position])
        }

        override fun getItemCount(): Int = differ.currentList.size

        inner class TMDBViewHolder(private val binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Result) {
                binding.cardTitleValue.text = item.title
                binding.cardOverviewValue.text = item.overview
                Glide.with(itemView.context)
                    .load(BuildConfig.BASE_URL_IMAGE + item.posterPath)
                    .into(binding.thumbMovie)

                binding.itemData.setOnClickListener {
                    onClickListers.invoke(item.id, item)
                }
            }
        }
}