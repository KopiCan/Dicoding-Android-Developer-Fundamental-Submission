package com.dicoding.submission3

import com.google.gson.annotations.SerializedName


data class UserResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val githubUser: List<GithubUser>

)

data class GithubUser(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String,
)

