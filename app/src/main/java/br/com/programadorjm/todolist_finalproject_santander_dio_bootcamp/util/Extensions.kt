package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.util

import android.graphics.Paint
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.R
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskEntity


fun TaskEntity.toTaskModel():TaskModel = TaskModel(this.TaskId, this.taskName, this.taskCheck)

fun TaskModel.toTaskEntity():TaskEntity = TaskEntity(this.taskId, this.taskName, this.taskCheck)

fun List<TaskEntity>.toListTaskModel():List<TaskModel>{
    val listTaskModel = mutableListOf<TaskModel>()
    this.forEach { taskEntity -> listTaskModel.add(taskEntity.toTaskModel()) }
    return listTaskModel
}

@BindingAdapter("isChecked")
fun TextView.isChecked(taskModel: TaskModel){
    if (taskModel.taskCheck){
        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0,0,0)
        isEnabled = false
        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }
}