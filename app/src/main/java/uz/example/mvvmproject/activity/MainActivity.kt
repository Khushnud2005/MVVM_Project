package uz.example.mvvmproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.example.mvvmproject.R
import uz.example.mvvmproject.adapter.PostAdapter
import uz.example.mvvmproject.model.Post
import uz.example.mvvmproject.network.RetrofitHttp
import uz.example.mvvmproject.utils.Utils
import uz.example.mvvmproject.utils.Utils.toast
import uz.example.mvvmproject.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var floating: FloatingActionButton
    var posts = ArrayList<Post>()
    lateinit var pb_loading: ProgressBar
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        pb_loading = findViewById(R.id.pb_loading)
        floating = findViewById(R.id.floating)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        floating.setOnClickListener { openCreateActivity() }
        viewModel.apiPostList()
        viewModel.allPosts.observe(this) {
            refreshAdapter(it)
        }

        val extras = intent.extras
        if (extras != null) {
            pb_loading.visibility = View.VISIBLE
            Toast.makeText(this@MainActivity,"${extras.getString("title")} Edited", Toast.LENGTH_LONG).show()
            viewModel.apiPostList()
        }
    }

    private fun refreshAdapter(posts: ArrayList<Post>) {
        val adapter = PostAdapter(this, posts)
        recyclerView.setAdapter(adapter)
        pb_loading.visibility = View.GONE
    }
    fun openCreateActivity() {
        val intent = Intent(this@MainActivity, CreateActivity::class.java)
        launchCreateActivity.launch(intent)
    }

    var launchCreateActivity = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            pb_loading.visibility = View.VISIBLE
            if (result.data != null) {
                val title = result.data!!.getStringExtra("title")
                toast(this@MainActivity,"$title Created")
                viewModel.apiPostList()
            }
        } else {
            Toast.makeText(this@MainActivity, "Operation canceled", Toast.LENGTH_LONG).show()
        }
    }






}