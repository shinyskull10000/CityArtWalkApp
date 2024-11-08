package com.example.cityartwalkapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.core.content.ContextCompat

class GPSAdapter (private val context: Context,
                  private val activity: Activity,
                  private val locationTextView: TextView,
                  private val fragment: Fragment? = null) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var latitude: Double? = null
    private var longitude: Double? = null

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    fun initializePermissionLauncher(fragment: androidx.fragment.app.Fragment) {
        requestPermissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(context, "Location permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission using ActivityResultLauncher
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        // Fetch the last known location
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                locationTextView.text = context.getString(R.string.gps_location, latitude, longitude)
                // Save location to the database if needed
            } else {
                Toast.makeText(context, "Could not retrieve location", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun openMap() {
        val location = locationTextView.text.toString()
        if (location.isNotBlank() && location.contains(",")) {
            val uri = Uri.parse("geo:0,0?q=${Uri.encode(location)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                Toast.makeText(context, "Google Maps app is not available.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "No GPS location available.", Toast.LENGTH_SHORT).show()
        }
    }
}