package uz.example.mvvmproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import uz.example.mvvmproject.R
import uz.example.mvvmproject.model.Post
import uz.example.mvvmproject.viewmodel.EditViewModel

class EditActivity : AppCompatActivity() {
    lateinit var et_idUser: EditText
    lateinit var et_title: EditText
    lateinit var et_post: EditText
    lateinit var btn_edit: Button
    lateinit var id: String

    lateinit var viewModel:EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initViews()
    }
    fun initViews(){
        viewModel = ViewModelProvider(this)[EditViewModel::class.java]
        val extras = intent.extras
        et_idUser = findViewById(R.id.et_userIdEdit)
        et_title = findViewById(R.id.et_titleEdit)
        et_post = findViewById(R.id.et_textEdit)
        btn_edit = findViewById(R.id.btn_submitEdit)
        if (extras != null) {
            Log.d("###", "extras not NULL - ")
            et_idUser.setText(extras.getString("user_id"))
            et_title.setText(extras.getString("title"))
            et_post.setText(extras.getString("body"))
            id = extras.getString("id")!!
        }
        btn_edit.setOnClickListener {
            val title = et_title.text.toString()
            val body = et_post.text.toString().trim { it <= ' ' }
            val id_user = et_idUser.text.toString().trim { it <= ' ' }

            if (title.isNotEmpty() && body.isNotEmpty() && id_user.isNotEmpty()){
                val post = Post(id.toInt(),id_user.toInt(), title, body)
                Log.d("@@EditActivity","Pot model Created")
                viewModel.apiPostUpdate(post)
                viewModel.editedPost.observe(this){
                    Log.d("@@EditActivity","${it.title} Post Edited")
                    val intent = Intent(this@EditActivity, MainActivity::class.java)
                    intent.putExtra("title", it.title)
                    startActivity(intent)
                }
            }
        }
    }
}