package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.databinding.TaskListLayoutBinding

class TaskAdapter(var editListener:(taskModel:TaskModel)->Unit = {}, var deleteListener:(taskModel:TaskModel)->Unit = {}):ListAdapter<TaskModel, TaskAdapter.TaskViewHolder>(TaskModelCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position), editListener, deleteListener)
    }


    class TaskViewHolder(private val binding: TaskListLayoutBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(
            taskModel: TaskModel,
            editListener: (TaskModel) -> Unit,
            deleteListener: (TaskModel) -> Unit
        ) {
            binding.taskModel = taskModel
            binding.btnEdit.setOnClickListener { editListener(taskModel) }
            binding.btnDelete.setOnClickListener { deleteListener(taskModel) }

        }
    }

    class TaskModelCallback: DiffUtil.ItemCallback<TaskModel>() {
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.taskName == newItem.taskName && oldItem.taskCheck == newItem.taskCheck
        }

    }
}
