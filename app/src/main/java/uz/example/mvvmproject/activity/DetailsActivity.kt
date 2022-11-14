package uz.example.mvvmproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import uz.example.mvvmproject.R
import uz.example.mvvmproject.viewmodel.DetailViewModel
import uz.example.mvvmproject.viewmodel.EditViewModel

class DetailsActivity : AppCompatActivity() {
    lateinit var tv_title: TextView
    lateinit var tv_body: TextView

    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        tv_title = findViewById(R.id.tv_title_detail)
        tv_body = findViewById(R.id.tv_body_detail)
        val extras = intent.extras
        if (extras != null) {
            Log.d("###", "extras not NULL - ")
            val id = extras.getInt("id")
            viewModel.apiPostDetail(id)
            viewModel.detailPost.observe(this){
                tv_title.setText(it.title.uppercase())
                tv_body.setText(it.body)
            }

        }
    }
}