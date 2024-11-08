package com.example.cityartwalkapp

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.cityartwalkapp.database.ArtDatabase
import com.example.cityartwalkapp.database.ArtEntryDAO
import java.util.UUID

class ArtRepository(application: Application) {
    private val artDao: ArtEntryDAO

    init {
        val db = ArtDatabase.getDatabase(application)
        artDao = db.artEntryDao()
    }

    fun getArtEntries(): LiveData<List<ArtEntry>> = artDao.getAllEntries()
    suspend fun insertArtEntry(artEntry: ArtEntry) {
        artDao.insertEntry(artEntry)
    }

    fun getArtEntryById(id: UUID): LiveData<ArtEntry?> {
        return artDao.getArtEntryById(id)
    }

    suspend fun deleteEntrybyId(entryId: UUID) {
        artDao.deleteEntryById(entryId)
    }
}