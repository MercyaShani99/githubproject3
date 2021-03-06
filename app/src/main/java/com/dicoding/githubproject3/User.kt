package com.dicoding.githubproject3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatar: String? = null,
    var name: String? = null,
    var username: String? = null,
    var company: String? = null,
    var location: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var repository: String? = null
) : Parcelable
