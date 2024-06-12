package com.moviedb.presentation.fragments.info

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.moviedb.data.response.Genre
import com.moviedb.databinding.FragmentHomeBinding
import com.moviedb.databinding.FragmentInfoMovieBinding
import com.moviedb.presentation.fragments.inicio.HomeViewModel
import com.moviedb.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class InfoMovieFragment : Fragment() {
    private var _binding: FragmentInfoMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel:InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("ID")?.let { viewModel.detailsMovie(it) }
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            binding.tvTitle.text = it.title
            binding.tvDescription.text = it.overview
            binding.tvDuration.text = "Duración: ${it.runtime}min"
            val dateStr = SimpleDateFormat("yyyy-MM-dd")
            val date = dateStr.parse(it.release_date).toString().split(" ")
            binding.tvEstreno.text = "Estreno: ${date[2]} de ${date[1]} del ${date[5]}"
            binding.tvGenero.text = "Géneros: ${generos(it.genres)}"
            Glide.with(requireContext())
                .load(Constants.URL_IMAGE+it.backdrop_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imvImageDescription)
        })

    }

    private fun generos(data:List<Genre>):String{
        var gnos = ""
        data.forEachIndexed { index, genre ->
            gnos += genre.name
            if (index+1 != data.size){
                gnos += ", "
            }
        }
        return gnos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}