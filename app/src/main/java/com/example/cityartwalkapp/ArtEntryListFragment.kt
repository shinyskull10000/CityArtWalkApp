package com.example.cityartwalkapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cityartwalkapp.databinding.FragmentArtEntryListBinding

class ArtEntryListFragment : Fragment() {

    private var _binding: FragmentArtEntryListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArtListAdapter

    private val artDataViewModel: ArtDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtEntryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArtListAdapter { artEntryId ->
            (activity as? MainActivity)?.openArtDetailFragment(artEntryId)
        }

        binding.artEntriesRecyclerView.adapter = adapter


        artDataViewModel.artEntries.observe(viewLifecycleOwner) { entries ->
            adapter.submitList(entries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}