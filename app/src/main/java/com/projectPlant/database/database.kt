package com.projectPlant.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projectPlant.DAO.MyPlantDAO
import com.projectPlant.DAO.PlantDAO
import com.projectPlant.model.Plant
import com.projectPlant.model.PlantSimple

@Database(
    entities = [
        PlantSimple::class, Plant::class
    ], version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract val plantDAO: PlantDAO
    abstract val myPlantDAO: MyPlantDAO


    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}