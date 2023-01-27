package com.dicoding.submission3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUser (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "login")
    var login: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null
)
