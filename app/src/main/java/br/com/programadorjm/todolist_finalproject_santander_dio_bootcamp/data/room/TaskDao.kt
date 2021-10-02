package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY task_check ASC")
    suspend fun getAllTask(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(taskEntity: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)
}