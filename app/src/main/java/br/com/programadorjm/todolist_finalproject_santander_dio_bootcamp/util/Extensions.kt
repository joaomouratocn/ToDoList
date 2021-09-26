package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.util

import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import org.w3c.dom.Entity


fun TaskEntity.toTaskModel():TaskModel = TaskModel(this.TaskId, this.taskName, this.taskCheck)

fun TaskModel.toTaskEntity():TaskEntity = TaskEntity(this.taskId, this.taskName, this.taskCheck)

suspend fun Flow<List<TaskEntity>>.toTaskModel():Flow<List<TaskModel>>{
    return transform { collect { taskEntityList ->
        for (item in taskEntityList) item.toTaskModel()
    } }
}