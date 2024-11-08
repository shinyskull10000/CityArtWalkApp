package com.example.cityartwalkapp

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cityartwalkapp.databinding.ListItemArtDataBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID


class ArtListAdapter(
    private val onArtEntryClicked: (UUID) -> Unit
) : ListAdapter<ArtEntry, ArtListAdapter.ArtEntryViewHolder>(ArtEntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtEntryViewHolder {
        val binding = ListItemArtDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtEntryViewHolder(binding, onArtEntryClicked)
    }

    override fun onBindViewHolder(holder: ArtEntryViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry)
    }

    class ArtEntryViewHolder(
        private val binding: ListItemArtDataBinding,
        private val onArtEntryClicked: (UUID) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: ArtEntry) {
            binding.titleTextView.text = entry.title
            binding.dateTextView.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(entry.date)
            binding.addressTextView.text = entry.address
            binding.root.setOnClickListener{
                onArtEntryClicked(entry.id)
            }
        }
    }

    class ArtEntryDiffCallback : DiffUtil.ItemCallback<ArtEntry>() {
        override fun areItemsTheSame(oldItem: ArtEntry, newItem: ArtEntry) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ArtEntry, newItem: ArtEntry) = oldItem == newItem
    }
}