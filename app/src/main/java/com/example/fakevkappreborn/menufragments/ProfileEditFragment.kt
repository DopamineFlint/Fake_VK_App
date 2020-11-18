package com.example.fakevkappreborn.menufragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fakevkappreborn.BottomSheetDialog
import com.example.fakevkappreborn.MainActivity
import com.example.fakevkappreborn.R
import com.example.fakevkappreborn.items.UserItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class ProfileEditFragment : Fragment(R.layout.fragment_profile_edit) {

    private val path = "user.json"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyCreateViewLog", "OnCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MyCreateViewLog", "OnCreateEDView")

        val act = activity as MainActivity

        val gson = Gson()
        val fromJson: String? = act.loadJSONFromAsset(context, "user.json")

        if (gson.fromJson(fromJson, UserItem::class.java) != null) {
            val userItem: UserItem = gson.fromJson(fromJson, UserItem::class.java)
            vk_profile_edit_name.setText(userItem.name, TextView.BufferType.EDITABLE)
            vk_profile_edit_status.setText(userItem.status, TextView.BufferType.EDITABLE)
        } else {
            vk_profile_edit_name.setText("ERROR", TextView.BufferType.EDITABLE)
            vk_profile_edit_status.setText("ERROR", TextView.BufferType.EDITABLE)
        }

        vk_profile_edit_custom_avatar.setImageBitmap(loadImageFromInternalStorage())

        vk_finish_edit_button.setOnClickListener {
            val newName: String = vk_profile_edit_name.text.toString()
            val newStatus: String = vk_profile_edit_status.text.toString()
            Log.d("MyTestLog", "$newName $newStatus")

            if (newName.trim().isEmpty()) {
                Toast.makeText(context, "Name field must not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                val toUserItem = UserItem(newName, newStatus)
                val toJson: String = gson.toJson(toUserItem)
                Log.d("MyTestLog", toJson)

                act.saveText(toJson, path)

                //fragmentManager?.popBackStack()
                childFragmentManager.popBackStack()
            }
        }

        vk_cancel_edit_button.setOnClickListener {
            //fragmentManager?.popBackStack()
            childFragmentManager.popBackStack()
        }

        vk_profile_edit_custom_avatar.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog()

            childFragmentManager.let { it1 ->
                bottomSheetDialog.show(
                    it1,
                    "exampleBottomSheet"
                )
            } //Need to fix this

            /*fragmentManager?.let { it1 ->
                bottomSheetDialog.show(
                    it1,
                    "exampleBottomSheet"
                )
            }*/

            /*val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)*/*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            Log.d("MyPhotoLog", "${data?.data}")
            val imageUri = data?.data
            if (imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
                saveToInternalStorage(bitmap)
                vk_profile_edit_custom_avatar.setImageBitmap(bitmap)
            }
        }
    }

    private fun saveToInternalStorage(bImage: Bitmap) { //save selected photo to InternalStorage
        val directory = context?.getDir("imageDir", Context.MODE_PRIVATE)
        val myPath = File(directory, "vk_avatar_profile.jpg")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)

            bImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Log.d("MyPhotoLog", "${directory?.absolutePath}")
    }

    private fun loadImageFromInternalStorage(): Bitmap { //load main profile avatar photo
        val f =
            File("/data/user/0/com.example.fakevkappreborn/app_imageDir", "vk_avatar_profile.jpg")
        return if (f.exists()) {
            BitmapFactory.decodeStream(FileInputStream(f))
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.photo_of_me)
        }
    }

    fun setPhoto(bitmap: Bitmap) {
        vk_profile_edit_custom_avatar.setImageBitmap(bitmap)
    }
}