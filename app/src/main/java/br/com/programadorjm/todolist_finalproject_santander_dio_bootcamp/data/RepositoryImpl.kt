package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data

import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskDao
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskEntity
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.util.toTaskEntity
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val taskDao: TaskDao):Repository {
    override fun getAllTask(): Flow<List<TaskEntity>> {
        return taskDao.getAllTask()
    }

    override suspend fun saveTask(taskModel: TaskModel) {
        taskDao.saveTask(taskModel.toTaskEntity())
    }

    override suspend fun deleteTask(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toTaskEntity())
    }
}