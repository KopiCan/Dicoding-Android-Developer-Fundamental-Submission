package com.dicoding.submission3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission3.databinding.ItemRowUserBinding

class SearchAdapter(private val listUser: List<GithubUser>) :RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var onItemCallback: OnItemCallback

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(user.avatarUrl)
                .into(imgAvatarRow)
            tvNameUserRow.text = user.login

            root.setOnClickListener {onItemCallback.onItemClicked(listUser[position])}
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemCallback {
        fun onItemClicked(data: GithubUser)
    }

    fun setOnItemCallback(onItemCallback: OnItemCallback){
        this.onItemCallback = onItemCallback
    }

}
