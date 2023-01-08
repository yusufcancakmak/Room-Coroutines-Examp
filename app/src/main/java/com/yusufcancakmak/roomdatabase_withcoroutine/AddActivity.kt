package com.yusufcancakmak.roomdatabase_withcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yusufcancakmak.roomdatabase_withcoroutine.databinding.ActivityAddBinding
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Note is a non-null type,
        // so the cast to it triggers a null check. Since you're comparing note to null manually afterwards,
        // probably you meant the safe cast operator,
        // which yields null if the expression is not of the type specified in the right-hand side:
        user = intent.getSerializableExtra("Data") as? User

        if (user == null) binding.btnAddOrUser.text = "Add User"
        else {
            binding.btnAddOrUser.text = "update"
            binding.edFirtname.setText(user?.firstname.toString())
            binding.edLastname.setText(user?.lastName.toString())
        }

        binding.btnAddOrUser.setOnClickListener {
            adduser()

        }

    }

    private fun adduser() {
        val firstName = binding.edFirtname.text.toString()
        val lastName = binding.edLastname.text.toString()
        lifecycleScope.launch {
            if (user == null) {
                val user = User(firstname = firstName, lastName = lastName)
                AppDatabase(this@AddActivity).getUserDao().addUser(user)
                finish()

            } else {
                val y = User(firstName, lastName)
                y.id = user?.id ?: 0
                AppDatabase(this@AddActivity).getUserDao().updateUser(y)
                finish()
            }

        }
    }
}