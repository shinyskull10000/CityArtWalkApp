package com.example.cityartwalkapp

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class ImageZoomDialogFragment(private val imagePath: String?) : DialogFragment() {

    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_image_zoom)
            val imageView: ImageView = findViewById(R.id.zoomedImageView)

            // Load the image into the ImageView
            imagePath?.let {
                val bitmap = BitmapFactory.decodeFile(it)
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}