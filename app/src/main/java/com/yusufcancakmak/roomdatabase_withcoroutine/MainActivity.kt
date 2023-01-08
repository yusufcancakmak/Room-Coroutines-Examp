package com.yusufcancakmak.roomdatabase_withcoroutine

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufcancakmak.roomdatabase_withcoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var madapter:UserAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent=Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setAdapter(data:List<User>){
        madapter?.setData(data)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {

        val userList=AppDatabase(this@MainActivity).getUserDao().getAllUser()
            madapter= UserAdapter()
            binding.rv.apply {
                layoutManager=LinearLayoutManager(this@MainActivity)
                adapter=madapter
                    setAdapter(userList)

                    madapter?.setOnActionListener {
                        val intent=Intent(this@MainActivity, AddActivity::class.java)
                        intent.putExtra("Data",it)
                        startActivity(intent)
                    }
                    madapter?.setOnActionDeleteListener {
                        val builder=AlertDialog.Builder(this@MainActivity)
                        builder.setMessage("Are you sure yo want?")
                        builder.setPositiveButton("YES"
                        ) { p0, p1 ->
                            lifecycleScope.launch {
                                AppDatabase(this@MainActivity).getUserDao().deleteUser(it)
                                val list=AppDatabase(this@MainActivity).getUserDao().getAllUser()
                            setAdapter(list)
                            }
                            p0.dismiss()

                        }
                        builder.setNegativeButton("NO"){ p0, p1 ->
                            p0.dismiss()
                        }
                        val dialog = builder.create()
                        dialog.show()

                    }
                }
            }
        }

    }
