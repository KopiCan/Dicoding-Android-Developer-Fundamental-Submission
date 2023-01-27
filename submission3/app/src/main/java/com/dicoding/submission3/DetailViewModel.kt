package com.dicoding.submission3

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val _listDetail = MutableLiveData<DataResponse>()
    val listDetail: LiveData<DataResponse> = _listDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int> = _state

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }
    fun delete(id: Int){
        mFavoriteUserRepository.delete(id)
    }

    fun getFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getFavorites()

    internal fun getDetailUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(login)
        client.enqueue(object : Callback<DataResponse> {
            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listDetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

}
