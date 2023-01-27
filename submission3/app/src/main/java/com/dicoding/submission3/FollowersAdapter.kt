package com.dicoding.submission3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission3.databinding.ItemRowUserBinding

class FollowersAdapter(private val listFollowers: List<GithubUser>) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val followers = listFollowers[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(followers.avatarUrl)
                .into(imgAvatarRow)
            tvNameUserRow.text = followers.login
        }
    }

    override fun getItemCount(): Int = listFollowers.size

}
