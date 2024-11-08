package com.example.cityartwalkapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File
import java.io.IOException

class PhotoAdapter(
    private val fragment: Fragment,
    private val imageView: ImageView,
    private val context: Context,
    private val cameraLauncher: ActivityResultLauncher<Intent>
) {
    var photoFile: File? = null

    // Open the camera to take a photo
    fun captureImage() {
        val photoFile = createImageFile(context)
        this.photoFile = photoFile
        photoFile?.let { file ->
            val photoURI: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
            cameraLauncher.launch(intent)
        }
    }

    // Display the photo in the ImageView
    fun displayImage() {
        photoFile?.let {
            val bitmap = BitmapFactory.decodeFile(it.absolutePath)
            imageView.setImageBitmap(bitmap)
        }
    }

    // Create an image file to store the captured photo
    private fun createImageFile(context: Context): File? {
        val storageDir: File? = context.getExternalFilesDir(null)
        return try {
            File.createTempFile("JPEG_${System.currentTimeMillis()}", ".jpg", storageDir)
        } catch (ex: IOException) {
            null
        }
    }
}