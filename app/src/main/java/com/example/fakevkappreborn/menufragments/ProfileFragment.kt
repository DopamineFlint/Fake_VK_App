package com.example.fakevkappreborn.menufragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.fakevkappreborn.MainActivity
import com.example.fakevkappreborn.R
import com.example.fakevkappreborn.items.UserItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val act = activity as MainActivity

        val gson = Gson()
        val json: String? = act.loadJSONFromAsset(context, "user.json")
        val userItem: UserItem = gson.fromJson(json, UserItem::class.java)

        vk_user_name.text = userItem.name
        vk_status_message.text = userItem.status
        vk_profile_toolbar.title = userItem.email
        vk_custom_avatar.setImageBitmap(loadImageFromInternalStorage())

        vk_edit_button.setOnClickListener {
            val transaction: Int? =
            fragmentManager?.beginTransaction()?.replace(
                R.id.fragment_container,
                ProfileEditFragment()
            )?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)?.commit()
            Log.d("MyTransLog","$transaction")
        }

        /*
        vk_edit_photo.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }
        */*/

        /*vk_edit_photo_camera.setOnClickListener {
            askCameraPermissions()
        } */
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            Log.d("MyPhotoLog", "${data?.data}")
            val imageUri = data?.data
            if (imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
                saveToInternalStorage(bitmap)
                vk_custom_avatar.setImageBitmap(bitmap)
            }
        }

        if (requestCode == 102) {
            val bitmap= data?.extras?.get("data") as Bitmap
            saveToInternalStorage(bitmap)
            vk_custom_avatar.setImageBitmap(bitmap)
        }
    }
    
    private fun askCameraPermissions() {
        openCamera()
    }

    private fun openCamera() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("MyTestLog", "SD card is not available: ${Environment.getExternalStorageState()}")
            return
        }

        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 102)
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
        val f = File("/data/user/0/com.example.fakevkappreborn/app_imageDir", "vk_avatar_profile.jpg")
        return if (f.exists()) {
            BitmapFactory.decodeStream(FileInputStream(f))
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.photo_of_me)
        }
    }
}