package com.example.cityartwalkapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cityartwalkapp.databinding.FragmentArtDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

//import com.example.cityartwalkapp.databinding.FragmentArtDetailBinding

class ArtDetailFragment: Fragment() {

    private var _binding: FragmentArtDetailBinding? = null
    private val binding get() = _binding!!
    private val artDataViewModel: ArtDataViewModel by activityViewModels()
    private lateinit var artEntryId: UUID


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        artEntryId = arguments?.getSerializable(ARG_ART_ENTRY_ID) as UUID

        artDataViewModel.getArtEntryById(artEntryId).observe(viewLifecycleOwner) { artEntry ->
            if (artEntry != null) {
                bindData(artEntry)
            }
        }
        binding.deleteButton.setOnClickListener{
            deleteEntry()
        }
        binding.imageView.setOnClickListener {
            artDataViewModel.getArtEntryById(artEntryId).observe(viewLifecycleOwner) { artEntry ->
                artEntry?.imageUri?.let { imagePath ->
                    val dialog = ImageZoomDialogFragment(imagePath)
                    dialog.show(parentFragmentManager, "imageZoom")
                }
            }
        }
    }

    private fun bindData(artEntry: ArtEntry) {
        binding.titleTextView.text = artEntry.title
        binding.dateTextView.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(artEntry.date)
        binding.addressTextView.text = artEntry.address
        binding.gpsLocationTextView.text = artEntry.gpsLocation

        artEntry.imageUri?.let { imagePath ->
            val imageUri = Uri.parse(imagePath)
            binding.imageView.setImageURI(imageUri)
        }
    }
    private fun deleteEntry() {
        artDataViewModel.deleteEntrybyID(artEntryId)
        parentFragmentManager.popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val ARG_ART_ENTRY_ID = "art_entry_id"
        fun newInstance(artEntryId: UUID): ArtDetailFragment {
            return ArtDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ART_ENTRY_ID, artEntryId)
                }
            }
        }
    }

}