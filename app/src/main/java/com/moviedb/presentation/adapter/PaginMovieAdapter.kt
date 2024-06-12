package com.moviedb.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.moviedb.data.response.Result
import com.moviedb.databinding.ItemMoviesBinding
import com.moviedb.utils.Constants
import java.math.RoundingMode
import java.text.DecimalFormat

class PaginMovieAdapter(
    var context: Context,
    private val mListener: OnItemClickListener
) : PagingDataAdapter<Result, PaginMovieAdapter.ViewHolder>(NotiDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemMoviesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(var itemview: ItemMoviesBinding) : RecyclerView.ViewHolder(itemview.root) {
        fun bind(movie: Result) {
            itemview.tvMovie.text = movie.title
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            itemview.tvRaiting.text = df.format(movie.vote_average).toString()
            Glide.with(context)
                .load(Constants.URL_IMAGE+movie.poster_path)
                .apply(RequestOptions().override(330,330))
                .fitCenter()
                .into(itemview.imvImageMovie)
            itemView.setOnClickListener {
                mListener.onClickMovie(movie)
            }
        }
    }
    interface OnItemClickListener{
        fun onClickMovie(modelo: Result)
    }
}

class NotiDiffCallback:DiffUtil.ItemCallback<Result>(){
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}