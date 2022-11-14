package uz.example.mvvmproject.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.example.mvvmproject.model.Post
import uz.example.mvvmproject.network.RetrofitHttp

class CreateViewModel :ViewModel() {
    val newPost = MutableLiveData<Post>()

    fun apiPostCreate(post: Post) {
        RetrofitHttp.postService.createPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                newPost.value = response.body()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("@@@", t.toString())
            }
        })
    }
}