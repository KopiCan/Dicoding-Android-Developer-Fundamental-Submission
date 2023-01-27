package com.dicoding.submission3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission3.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteUserBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getFavorites().observe(this, {favoriteList ->
            if (favoriteList != null)
                adapter.setFavorites(favoriteList)
        })
        adapter = FavoriteUserAdapter()
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(false)
        binding?.rvFavorite?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}