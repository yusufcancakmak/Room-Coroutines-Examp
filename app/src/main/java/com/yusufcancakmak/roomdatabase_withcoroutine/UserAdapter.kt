package com.yusufcancakmak.roomdatabase_withcoroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufcancakmak.roomdatabase_withcoroutine.databinding.CardItemUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private var list = mutableListOf<User>()
    private var actionEdit:((User)->Unit)?=null
    private var actionDelete:((User)->Unit)? = null

    inner class MyViewHolder(val binding: CardItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CardItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curentlist = list[position]

        holder.binding.tvFirstName.text = curentlist.firstname
        holder.binding.tvLastName.text = curentlist.lastName
        holder.binding.delete.setOnClickListener { actionDelete?.invoke(curentlist) }
        holder.binding.update.setOnClickListener { actionEdit?.invoke(curentlist) }
    }

    override fun getItemCount() = list.size

    fun setOnActionListener(callback:(User)->Unit){
        this.actionEdit=callback
    }
    fun setOnActionDeleteListener(callback: (User) -> Unit){
        this.actionDelete=callback
    }

     fun setData(data:List<User>){
        list.apply {
            clear()
            addAll(data)
        }
    }
}