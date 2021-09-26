package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val TaskId:Long,
    @ColumnInfo(name = "task_name") val taskName:String,
    @ColumnInfo(name = "task_check", defaultValue = "false") val taskCheck:Boolean
)