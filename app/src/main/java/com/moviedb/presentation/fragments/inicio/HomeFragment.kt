package com.moviedb.presentation.fragments.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.moviedb.R
import com.moviedb.data.response.Result
import com.moviedb.databinding.FragmentHomeBinding
import com.moviedb.presentation.adapter.PaginMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PaginMovieAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel:HomeViewModel by viewModels()
    private lateinit var paginMovieAdapter: PaginMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,3)
            paginMovieAdapter = PaginMovieAdapter(context,this@HomeFragment)
            adapter = paginMovieAdapter
        }
        getMovies()
    }

    private fun getMovies() {
        viewModel.getListMovie().observe(viewLifecycleOwner) {
            it?.let {
                paginMovieAdapter.submitData(lifecycle,it)
            }
        }
    }

    override fun onClickMovie(modelo: Result) {
        val bundle = bundleOf("ID" to modelo.id)
        findNavController().navigate(R.id.navigation_info,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}