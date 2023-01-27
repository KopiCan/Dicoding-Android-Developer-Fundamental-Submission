package com.dicoding.submission3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {

    private val _githubUserList = MutableLiveData<List<GithubUser>>()
    val githubUserList: LiveData<List<GithubUser>> = _githubUserList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int> = _state

    internal fun searchGithubUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _githubUserList.value = response.body()?.githubUser
                        _totalCount.value = response.body()?.totalCount
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getTheme(): LiveData<Boolean> {
        return preferences.getTheme().asLiveData()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }


}