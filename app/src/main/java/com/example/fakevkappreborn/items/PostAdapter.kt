package com.example.fakevkappreborn.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fakevkappreborn.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.vk_post_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter(private val postItemList: List<PostItemTest>,
                  private val listener: (PostItemTest) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vk_post_item, parent, false)

        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = postItemList[position]

        holder.textView1.text = currentItem.username
        holder.textView2.text = currentItem.likes_count.toString()
        holder.textView3.text = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH).format(
            Date(currentItem.post_date.toLong() * 1000))
        holder.textView4.text = currentItem.comments_count.toString()
        holder.textView5.text = currentItem.shares_count.toString()
        holder.textView6.text = currentItem.post_text
        holder.likeCheckbox.isChecked = currentItem.is_user_like
        Picasso.get().load(currentItem.avatar_url).into(holder.imageView1)
        Picasso.get().load(currentItem.post_image).into(holder.imageView2)

        holder.itemView.setOnClickListener { listener(currentItem) }
    }

    override fun getItemCount() = postItemList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.vk_name_family_name
        val textView2: TextView = itemView.vk_likes_counter
        val textView3: TextView = itemView.vk_post_date
        val textView4: TextView = itemView.vk_comments_counter
        val textView5: TextView = itemView.vk_reposts_counter
        val textView6: TextView = itemView.vk_post_text
        val likeCheckbox: CheckBox = itemView.vk_like_checkbox
        val imageView1: ImageView = itemView.vk_avatar_image
        val imageView2: ImageView = itemView.vk_image_view
    }
}