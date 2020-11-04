package com.example.fakevkappreborn.items

data class PostItemTest(
    val id: Int,
    val avatar_url: String?,
    val username: String,
    val post_date: Int,
    val post_text: String?,
    val post_image: String?,
    val is_user_like: Boolean,
    val likes_count: Int,
    val comments_count: Int,
    val shares_count: Int)