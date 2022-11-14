package uz.example.mvvmproject.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.example.mvvmproject.model.Post
import uz.example.mvvmproject.network.RetrofitHttp
import uz.example.mvvmproject.utils.Utils.toast

class MainViewModel : ViewModel() {

    val allPosts = MutableLiveData<ArrayList<Post>>()
    val deletedPost = MutableLiveData<Post>()

    fun apiPostList() {
        RetrofitHttp.postService.listPost().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(call: Call<ArrayList<Post>>, response: Response<ArrayList<Post>>) {
                //Log.d("@@@", response.body().toString())
                allPosts.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                Log.e("@@@", t.message.toString())
                allPosts.value = null
            }
        })
    }



    fun apiPostDelete(post: Post){
        RetrofitHttp.postService.deletePost(post.id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                deletedPost.value = response.body()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                deletedPost.value = null
            }
        })
    }
}