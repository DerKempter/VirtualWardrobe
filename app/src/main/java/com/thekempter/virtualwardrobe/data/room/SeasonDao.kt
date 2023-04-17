package com.thekempter.virtualwardrobe.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thekempter.virtualwardrobe.data.Season
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonDao {
    @Query("SELECT * FROM seasons")
    fun getAllSeasons(): Flow<List<Season>>

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

    @Query("SELECT seasons.* FROM seasons INNER JOIN clothing_item_season ON seasons.id = clothing_item_season.seasonId WHERE clothing_item_season.clothingItemId = :clothingId")
    fun getSeasonsForClothingItem(clothingId: Int): List<Season>
}
