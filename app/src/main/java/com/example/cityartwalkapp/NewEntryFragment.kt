package com.example.cityartwalkapp

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.cityartwalkapp.databinding.FragmentNewEntryBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import androidx.fragment.app.activityViewModels
import androidx.activity.viewModels
import java.util.Date

class NewEntryFragment: Fragment() {
    private var _binding: FragmentNewEntryBinding? = null
    private val binding get() = _binding!!
    private val artDataViewModel: ArtDataViewModel by activityViewModels()
    private lateinit var gpsAdapter: GPSAdapter
    private lateinit var photoAdapter: PhotoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gpsAdapter = GPSAdapter(
            context = requireContext(),
            activity = requireActivity(),
            locationTextView = binding.gpsLocationTextView
        )
        gpsAdapter.initializePermissionLauncher(this)


        val cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    photoAdapter.displayImage()
                }
            }

        photoAdapter = PhotoAdapter(
            fragment = this,
            imageView = binding.imageView,
            context = requireContext(),
            cameraLauncher = cameraLauncher
        )

        binding.dateButton.setOnClickListener {
            showDatePickerDialog()
        }

        binding.saveButton.setOnClickListener {
            if (binding.dateButton.text.toString() == "Select Date") {
                Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show()
            } else {
                saveEntry()}

        }

        binding.backButton.setOnClickListener() {
            navigateBackToList()
        }
        binding.showMapButton.setOnClickListener {
            gpsAdapter.openMap()
        }
        binding.getGpsButton.setOnClickListener {
            gpsAdapter.getLocation()
        }
        binding.takePhotoButton.setOnClickListener {
            photoAdapter.captureImage()
        }
        binding.imageView.setOnClickListener {
            val dialog = ImageZoomDialogFragment(photoAdapter.photoFile?.path)
            dialog.show(parentFragmentManager, "imageZoom")
        }


    }

    private fun saveEntry() {
        val inserttitle = binding.titleEditText.text.toString()
        val insertaddress = binding.addressEditText.text.toString()
        val date = binding.dateButton.text.toString()
        val parsedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date) ?: Date()
        val savegpsLocation = binding.gpsLocationTextView.text.toString()
        val imagePath = photoAdapter.photoFile?.path
        if (inserttitle.isNotEmpty() && insertaddress.isNotEmpty()) {
            val newArtEntry = ArtEntry(
                id = UUID.randomUUID(),
                title = inserttitle,
                address = insertaddress,
                date = parsedDate,
                gpsLocation = savegpsLocation,
                imageUri = imagePath
            )
            artDataViewModel.insertEntry(newArtEntry)
            navigateBackToList()
        } else {
            Toast.makeText(requireContext(), "Title and or Address cannot be left empty", Toast.LENGTH_SHORT).show()

        }
    }


    private fun navigateBackToList() {
        parentFragmentManager.popBackStack()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            binding.dateButton.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day).show()
    }
}