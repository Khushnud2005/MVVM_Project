package uz.example.mvvmproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.example.mvvmproject.model.Post
import uz.example.mvvmproject.network.RetrofitHttp

class DetailViewModel : ViewModel(){
    val detailPost = MutableLiveData<Post>()

    fun apiPostDetail(id: Int) {
        Log.d("@@DetailViewModel","$id Post Came To ViewModel")
        RetrofitHttp.postService.detailPost(id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.body() != null) {
                        Log.d("@@DetailViewModel","${response.body()!!.title} Post Responded")
                        detailPost.value = response.body()
                    } else {
                        Log.d("@@@", response.toString())
                    }
                }

                override fun onFailure(call: Call<Post?>, t: Throwable) {
                    Log.d("@@@", t.toString())
                }
            })
    }
}