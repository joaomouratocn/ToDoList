package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model

data class TaskModel(
    val taskId:Long = 0,
    val taskName:String,
    val taskCheck:Boolean = false
)