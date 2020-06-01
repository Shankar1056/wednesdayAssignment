package com.wednesdayassignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wednesdayassignment.data.model.ResultItems

@Dao
interface ArtistDao {

    @Query("SELECT * FROM ResultItems")
    fun loadAllArtist(): List<ResultItems>?

    @Insert
    fun insertArtist(artist: ArrayList<ResultItems>?)

    @Query("DELETE FROM ResultItems")
    fun deleteAll()
}