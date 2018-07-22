package com.example.jaime.habittrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.jaime.habittrainer.db.HabitDbTable

import kotlinx.android.synthetic.main.activity_create_habit.*
import kotlinx.android.synthetic.main.single_card.*
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {
    private val TAG = CreateHabitActivity::class.java.simpleName
    private val CHOOSE_IMAGE_REQUEST = 1
    private var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    fun storeHabit(v: View) {
        if (etTitle.isBlank() || etDescription.isBlank()) {
            Log.d(TAG, "No habit stored: title or description missing")
            displayErrorMessage("Your habit needs an engaging title and description")
            return
        } else if (imageBitMap == null) {
            Log.d(TAG, "No habit stored: image was missing")
            displayErrorMessage("Add a motivating picture to your habit")
            return
        }

        // Store the habit...
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()

        // use !! to just ensure that the value is not going to be null
        val habit = Habit(title, description, imageBitMap!!)

        val id = HabitDbTable(this).store(habit)

        if (id == -1L) {
            displayErrorMessage("Habit could not be stored...Let's not make this a habit")
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayErrorMessage(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }

    fun chooseImage(v: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose image for habit")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)

        Log.d(TAG, "Intent to choose image sent...")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null) {
            Log.d(TAG, "An image was chosen by the user")

            val bitmap = tryReadBitmap(data.data)

            bitmap?.let {
                this.imageBitMap = bitmap
                ivImage.setImageBitmap(bitmap)
                Log.d(TAG, "Read image bitmap and updated image view")
            }
        }
    }

    private fun tryReadBitmap(data: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}

private fun EditText.isBlank() = this.text.toString().isBlank()
