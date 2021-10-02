package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data

import androidx.lifecycle.LiveData
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskEntity

interface Repository {
    suspend fun getAllTask():List<TaskModel>

    suspend fun saveTask(taskModel: TaskModel)

    suspend fun updateTask(taskModel: TaskModel)

    suspend fun deleteTask(taskModel: TaskModel)
}