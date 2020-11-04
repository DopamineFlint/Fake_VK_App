package com.example.fakevkappreborn.menufragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakevkappreborn.R
import com.example.fakevkappreborn.items.PostAdapter
import com.example.fakevkappreborn.items.PostItem
import com.example.fakevkappreborn.items.PostItemTest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_news.*
import java.io.IOException
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class NewsFragment : Fragment(R.layout.fragment_news) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gson = Gson()
        val jsonFileString: String? = context?.let { getJsonDataFromAsset(it, "vk_posts.json") }
        val listType = object : TypeToken<List<PostItemTest>>() {}.type
        val pitList: List<PostItemTest> = gson.fromJson(jsonFileString, listType)

        recycler_view.adapter = PostAdapter(pitList) {
            val bundle = Bundle()
            bundle.putInt("key_post_position", pitList.indexOf(it))
            bundle.putString("key_username", it.username)
            bundle.putString("key_avatar_url", it.avatar_url)
            bundle.putString("key_post_image", it.post_image)
            bundle.putString("key_post_text", it.post_text)
            bundle.putInt("key_comments_count", it.comments_count)
            bundle.putInt("key_likes_count", it.likes_count)
            bundle.putLong("key_post_date", it.post_date.toLong())
            bundle.putInt("key_shares_count", it.shares_count)
            bundle.putBoolean("key_is_liked", it.is_user_like)
            val postFragment = PostFragment()
            postFragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_menu_container,
                postFragment)?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)?.commit()
        }
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
            return null
        }
        return jsonString
    }
}