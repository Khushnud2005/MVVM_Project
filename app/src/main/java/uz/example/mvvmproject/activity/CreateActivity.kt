package uz.example.mvvmproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import uz.example.mvvmproject.R
import uz.example.mvvmproject.model.Post
import uz.example.mvvmproject.viewmodel.CreateViewModel

class CreateActivity : AppCompatActivity() {
    lateinit var et_idUser: EditText
    lateinit var et_title: EditText
    lateinit var et_post: EditText
    lateinit var btn_create: Button

    lateinit var viewModel: CreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        initViews()
    }

    private fun initViews() {
        et_idUser = findViewById(R.id.et_userIdCreate)
        et_title = findViewById(R.id.et_titleCreate)
        et_post = findViewById(R.id.et_textCreate)
        btn_create = findViewById(R.id.btn_submitCreate)

        viewModel = ViewModelProvider(this)[CreateViewModel::class.java]

        btn_create.setOnClickListener(View.OnClickListener {
            val title: String = et_title.getText().toString()
            val body: String = et_post.getText().toString().trim { it <= ' ' }
            val id_user: String = et_idUser.getText().toString().trim { it <= ' ' }
            if (title.isNotEmpty() && body.isNotEmpty() && id_user.isNotEmpty()){
                val post = Post(id_user.toInt(), title, body)
                viewModel.apiPostCreate(post)
                viewModel.newPost.observe(this){
                    val intent = Intent()
                    intent.putExtra("title", it.title)
                    setResult(RESULT_OK, intent)
                    super@CreateActivity.onBackPressed()
                }

            }

        })
    }
}