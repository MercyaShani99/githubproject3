package com.dicoding.githubproject3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailUserViewModel : ViewModel() {

    private val detailUser = MutableLiveData<User>()
    private val listFollowers = MutableLiveData<ArrayList<User>>()
    private val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setDetailUser(users: String) {
        val url = "https://api.github.com/users/$users"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d("ViewModel", result)
                try {
                    val responObjects = JSONObject(result)
                    val user = User()
                    user.name = responObjects.getString("name")
                    user.username = responObjects.getString("login")
                    user.avatar = responObjects.getString("avatar_url")
                    user.company = responObjects.getString("company")
                    user.location = responObjects.getString("location")
                    user.followers = responObjects.getString("followers")
                    user.following = responObjects.getString("following")
                    user.repository = responObjects.getString("public_repos")
                    detailUser.postValue(user)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }
    fun getDetailUser(): LiveData<User> {
        return detailUser
    }

    fun setFollowers(users: String?) {
        val listItemFollowers = ArrayList<User>()
        val url = "https://api.github.com/users/$users/followers"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d("ViewModel", result)
                    val responseObjects = JSONArray(result)
                    for (i in 0 until responseObjects.length()) {
                        val item = responseObjects.getJSONObject(i)
                        val user = User()
                        user.name = item.getString("login")
                        user.avatar = item.getString("avatar_url")
                        listItemFollowers.add(user)
                    }
                    listFollowers.postValue(listItemFollowers)
                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error!!.message.toString())
            }
        })
    }
    fun getFollowers(): LiveData<ArrayList<User>> {
        return listFollowers
    }

    fun setFollowing(users: String?) {
        val listItemFollowing = ArrayList<User>()
        val url = "https://api.github.com/users/$users/following"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d("ViewModel", result)
                    val response = JSONArray(result)
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i)
                        val user = User()
                        user.name = item.getString("login")
                        user.avatar = item.getString("avatar_url")
                        listItemFollowing.add(user)
                    }
                    listFollowing.postValue(listItemFollowing)
                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("Data error", error!!.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}


