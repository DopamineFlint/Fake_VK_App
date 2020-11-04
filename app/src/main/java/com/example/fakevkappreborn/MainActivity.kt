package com.example.fakevkappreborn

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fakevkappreborn.items.UserItem
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_FIRST_START = "KEY_FIRST_START"
        private const val MY_PREFS_FILE = "MyPrefsFile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings: SharedPreferences = getSharedPreferences(MY_PREFS_FILE, 0)
        val firstRun: Boolean = settings.getBoolean("firstRun", true)

        val gson = Gson()

        if (firstRun) {
            setContentView(R.layout.activity_main)

            val userItem = UserItem("Dopamine Flint", "GET YOUR ASS BACK HERE!")
            val json: String = gson.toJson(userItem)
            saveText(json, "user.json")

            val editor: SharedPreferences.Editor = settings.edit()
            editor.putBoolean("firstRun", false)
            editor.apply()
        } else {
            setContentView(R.layout.activity_main)
        }

        val newFragment: Fragment = LoginFragment()

        supportFragmentManager
            .beginTransaction().replace(R.id.fragment_container, newFragment).commit()
    }

    fun saveText(text: String, path: String?) {
        try {
            openFileOutput(path, MODE_PRIVATE).use { fileOutputStream ->
                fileOutputStream.write(
                    text.toByteArray()
                )
            }
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    fun loadJSONFromAsset(context: Context?, path: String?): String? {
        try {
            openFileInput(path).use { fileInputStream ->
                val br =
                    BufferedReader(InputStreamReader(fileInputStream))
                val sb = StringBuilder()
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    sb.append(line).append('\n')
                }
                br.close()
                return sb.toString()
            }
        } catch (e: IOException) {
            //You'll need to add proper error handling here
        }
        return ""
    }
}