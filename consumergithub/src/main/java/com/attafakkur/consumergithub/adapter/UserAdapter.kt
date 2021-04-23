package com.attafakkur.consumergithub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.attafakkur.consumergithub.R
import com.attafakkur.consumergithub.databinding.ItemUserBinding
import com.attafakkur.consumergithub.model.UserModel
import com.bumptech.glide.Glide

class UserAdapter(
    private val userAll: ArrayList<UserModel>,
    private val listener: (Any) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: UserModel?, listener: (Any) -> Unit) {
            if (user != null) {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .into(binding.userImage)

                binding.nameUser.text = user.username
                binding.type.text = user.type

                itemView.setOnClickListener { listener(user) }
                binding.more.setOnClickListener { listener(user) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userAll[position], listener)

    }

    override fun getItemCount() = userAll.size
}