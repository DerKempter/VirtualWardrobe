package com.thekempter.virtualwardrobe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SeasonDao {
    @Query("SELECT * FROM seasons")
    fun getAllSeasons(): List<Season>

    @Query("SELECT * FROM seasons WHERE id IN (:seasonIds)")
    fun getSeasonsByIds(seasonIds: List<Int>): List<Season>

    @Query("SELECT * FROM seasons WHERE name = :name")
    fun getSeasonByName(name: String): Season?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeasons(seasons: List<Season>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeason(season: Season)

    @Delete
    fun deleteSeason(season: Season)
}
