package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data

import androidx.lifecycle.LiveData
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskDao
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskEntity
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.util.toListTaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.util.toTaskEntity
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.util.toTaskModel
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val taskDao: TaskDao):Repository {
    override suspend fun getAllTask():List<TaskModel>{
        return taskDao.getAllTask().toListTaskModel()
    }

    override suspend fun saveTask(taskModel: TaskModel) {
        taskDao.saveTask(taskModel.toTaskEntity())
    }

    override suspend fun updateTask(taskModel: TaskModel) {
        taskDao.updateTask(taskModel.toTaskEntity())
    }

    override suspend fun deleteTask(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toTaskEntity())
    }
}