package com.projectPlant.DAO

import androidx.room.*
import com.projectPlant.model.Plant

@Dao
interface MyPlantDAO {
    @Insert
    fun insert(plant: Plant)

    @Delete
    fun delete(plant: Plant)

    @Query("DELETE FROM MY_PLANT WHERE id = :id")
    fun delete(id: Int)

    @Update
    fun update(plant: Plant)

    @Query("SELECT * from MY_PLANT WHERE idperson = :idPerson")
    fun get(idPerson: Int): List<Plant>
}