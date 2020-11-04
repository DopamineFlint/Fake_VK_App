package com.example.fakevkappreborn.menufragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fakevkappreborn.MainActivity
import com.example.fakevkappreborn.MainMenuFragment
import com.example.fakevkappreborn.R
import com.example.fakevkappreborn.items.UserItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile_edit.*


class ProfileEditFragment : Fragment(R.layout.fragment_profile_edit) {

    private val path = "user.json"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vk_finish_edit_button.setOnClickListener {
            val newName: String = vk_profile_edit_name.text.toString()
            val newStatus: String = vk_profile_edit_status.text.toString()
            Log.d("MyTestLog", "$newName $newStatus")

            val gson = Gson()

            if (newName.trim().isEmpty()) {
                Toast.makeText(context, "Name field must not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                val userItem = UserItem(newName, newStatus)
                val json: String = gson.toJson(userItem)
                Log.d("MyTestLog", json)

                val act = activity as MainActivity
                act.saveText(json, path)

                MainMenuFragment(1)

                fragmentManager?.popBackStack()
            }
        }

        vk_cancel_edit_button.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}