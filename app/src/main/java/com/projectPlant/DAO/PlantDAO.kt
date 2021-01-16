package com.projectPlant.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.projectPlant.model.PlantSimple

@Dao
interface PlantDAO {
    @Insert
    fun insert(simple: PlantSimple)

    @Query("SELECT * from PLANT WHERE id = :key")
    fun get(key: Int): PlantSimple
}