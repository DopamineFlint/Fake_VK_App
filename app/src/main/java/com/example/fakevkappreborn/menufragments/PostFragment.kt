package com.example.fakevkappreborn.menufragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fakevkappreborn.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_post.*
import java.text.SimpleDateFormat
import java.util.*

class PostFragment : Fragment(R.layout.fragment_post) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val username: String = bundle!!.getString("key_username")!!
        vk_selected_name_family_name.text = username

        val avatarURL: String = bundle.getString("key_avatar_url")!!
        Picasso.get().load(avatarURL).into(vk_selected_avatar_image)

        val postImage: String? = bundle.getString("key_post_image")
        Picasso.get().load(postImage).into(vk_selected_image_view)

        val postText: String? = bundle.getString("key_post_text")
        vk_selected_post_text.text = postText

        val commentsCount: Int = bundle.getInt("key_comments_count")
        vk_selected_comments_counter.text = commentsCount.toString()

        val likesCount: Int = bundle.getInt("key_likes_count")
        vk_selected_likes_counter.text = likesCount.toString()

        val postDate: Long = bundle.getLong("key_post_date")
        vk_selected_post_date.text = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH).format(
            Date(postDate * 1000)
        )

        val sharesCount: Int = bundle.getInt("key_shares_count")
        vk_selected_reposts_counter.text = sharesCount.toString()

        val isLiked: Boolean = bundle.getBoolean("key_is_liked")
        vk_selected_like_checkbox.isChecked = isLiked

        val currentPostPosition: Int = bundle.getInt("key_post_position")
        Log.d("MyTestLog", "$currentPostPosition")
    }
}