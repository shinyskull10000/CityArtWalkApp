package com.example.cityartwalkapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity (tableName = "art_entries")
data class ArtEntry(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "gps_location") val gpsLocation: String? = null,
    @ColumnInfo(name = "image_url") val imageUri: String? = null
)