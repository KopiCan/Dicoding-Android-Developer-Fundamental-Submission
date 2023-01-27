package com.dicoding.submission3

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

    class FavoriteUserViewModel(application: Application) : ViewModel() {
        private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

        fun getFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getFavorites()
    }
