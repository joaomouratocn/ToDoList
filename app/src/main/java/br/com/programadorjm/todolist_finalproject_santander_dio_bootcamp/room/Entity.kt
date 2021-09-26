package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entity(
    @PrimaryKey(autoGenerate = true) val id:Long,
    @ColumnInfo(name = "task_name") val taskName:String,
    @ColumnInfo(name = "task_check", defaultValue = "false") val taskCheck:Boolean
)