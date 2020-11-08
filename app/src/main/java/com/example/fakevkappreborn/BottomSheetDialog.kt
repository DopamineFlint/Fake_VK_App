package com.example.fakevkappreborn

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class BottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme) //makes this sheet transparent
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_sheet_camera_button.setOnClickListener {
            openCamera()
            //dismiss()
        }

        bottom_sheet_gallery_button.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)

            //dismiss()
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
                //vk_profile_edit_custom_avatar.setImageBitmap(bitmap)
            }
        }

        if (requestCode == 102) {
            val bitmap =
                data?.extras?.get("data") as Bitmap //check if it's null then paste diff photo
            saveToInternalStorage(bitmap)
            //vk_profile_edit_custom_avatar.setImageBitmap(bitmap)
        }
    }

    private fun openCamera() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("MyTestLog", "SD card is not available: ${Environment.getExternalStorageState()}")
            return
        }

        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 102)
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
}