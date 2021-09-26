package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data

import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllTask():Flow<List<TaskEntity>>

    suspend fun saveTask(taskModel: TaskModel)

    suspend fun deleteTask(taskModel: TaskModel)
}