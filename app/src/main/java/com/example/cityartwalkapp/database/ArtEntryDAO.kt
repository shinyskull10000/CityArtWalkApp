package com.example.cityartwalkapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cityartwalkapp.ArtEntry
import java.util.UUID

@Dao
interface ArtEntryDAO {
    @Query("SELECT * FROM art_entries ORDER BY date DESC")
    fun getAllEntries(): LiveData<List<ArtEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: ArtEntry)

    @Update
    suspend fun updateEntry(entry: ArtEntry)

    @Delete
    suspend fun deleteEntry(entry: ArtEntry)

    @Query("SELECT * FROM art_entries WHERE id = :id")
    fun getArtEntryById(id: UUID): LiveData<ArtEntry?>

    @Query("DELETE FROM art_entries WHERE id = :entryId")
    suspend fun deleteEntryById(entryId: UUID)
}