package com.example.cityartwalkapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityartwalkapp.database.ArtDatabase
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class ArtDataViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArtRepository = ArtRepository(application)

    val artEntries: LiveData<List<ArtEntry>> = repository.getArtEntries()

    fun insertEntry(entry: ArtEntry) = viewModelScope.launch {
        repository.insertArtEntry(entry)
    }

    fun getArtEntryById(id: UUID): LiveData<ArtEntry?> {
        return repository.getArtEntryById(id)
    }
    /*fun updateEntry(entry: ArtEntry) = viewModelScope.launch {
        artEntryDAO.updateEntry(entry)
    }

     */
    fun deleteEntrybyID(id: UUID) = viewModelScope.launch {
        repository.deleteEntrybyId(id)
    }

}