package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.room

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Entity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(entity: Entity)

    @Delete
    suspend fun deleteTask(entity: Entity)
}