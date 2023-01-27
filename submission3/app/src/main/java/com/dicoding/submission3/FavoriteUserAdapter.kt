package com.dicoding.submission3

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission3.databinding.ItemRowUserBinding

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private val favoriteList = ArrayList<FavoriteUser>()

    fun setFavorites(favoriteUser: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.favoriteList, favoriteUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.favoriteList.clear()
        this.favoriteList.addAll(favoriteUser)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteUserViewHolder(private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                tvNameUserRow.text = favoriteUser.login
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, favoriteUser.login)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(favoriteUser.avatarUrl)
                .into(binding.imgAvatarRow)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val itemRowUserBinding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val favoriteUser = favoriteList[position]
        holder.bind(favoriteUser)
    }

    override fun getItemCount(): Int = favoriteList.size
}
