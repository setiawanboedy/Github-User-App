package com.attafakkur.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.databinding.ItemUserBinding
import com.attafakkur.githubuserapp.model.DetailModel
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private val userAll: List<DetailModel>,
    private val listener: (Any) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: DetailModel, listener: (Any) -> Unit) {
            Glide.with(itemView.context)
                .load(user.avatar_url)
                .into(binding.userImage)

            binding.nameUser.text = user.username
            binding.type.text = user.type

            itemView.setOnClickListener { listener(user) }
            binding.more.setOnClickListener { listener(user) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userAll[position], listener)

    }

    override fun getItemCount() = userAll.size
}