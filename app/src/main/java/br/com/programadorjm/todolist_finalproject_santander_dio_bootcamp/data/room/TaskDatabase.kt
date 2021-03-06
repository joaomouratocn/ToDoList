package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase:RoomDatabase() {
    abstract fun dao():TaskDao

    companion object{
        private var INSTANCE:TaskDatabase? = null
        fun getDataBase(context: Context):TaskDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}