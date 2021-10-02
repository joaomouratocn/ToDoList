package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.R
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.RepositoryImpl
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.room.TaskDatabase
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.databinding.ActivityMainBinding
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.databinding.FragmentDialogNewTaskDialogBinding
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.ui.adapter.TaskAdapter
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var viewModel:MainViewModel
    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dataBase = TaskDatabase.getDataBase(this)
        val repositoryImpl = RepositoryImpl(dataBase.dao())
        val helper = ItemTouchHelper(
            ItemTouchHelper(0,
                androidx.recyclerview.widget.ItemTouchHelper.LEFT or
            androidx.recyclerview.widget.ItemTouchHelper.RIGHT)
        )

        adapter = TaskAdapter()
        adapter.editListener = {editTask(it)}
        adapter.deleteListener = {deleteTask(it)}

        helper.attachToRecyclerView(binding.mainRecycle)
        binding.mainRecycle.layoutManager = LinearLayoutManager(this)
        binding.mainRecycle.adapter = adapter

        viewModel = ViewModelProvider(
            viewModelStore,
            MainViewModel.ViewModelFactory(repositoryImpl)
        ).get(MainViewModel::class.java)

        binding.floatAddTask.setOnClickListener { showDialog(null) }

        viewModel.taskStateView.observe(this){
            when(it){
                is MainViewModel.TaskState.InvalidTaskName -> {
                    showToast(R.string.str_invalid_task_name)
                }
                is MainViewModel.TaskState.TaskIsChecked ->{
                    showToast(R.string.str_task_is_checked)
                    adapter.notifyDataSetChanged()
                }
                is MainViewModel.TaskState.TaskIsUnChecked ->{
                    showToast(R.string.str_task_is_Unchecked)
                    adapter.notifyDataSetChanged()
                }
                is MainViewModel.TaskState.SaveSuccess ->{
                    showToast(R.string.str_task_saved)
                }
                is MainViewModel.TaskState.UpdateSuccess ->{
                    showToast(R.string.str_task_checked)
                }
                is MainViewModel.TaskState.DeleteSuccess ->{
                    val taskModel = it.taskModel
                    Snackbar.make(binding.floatAddTask, R.string.str_task_deleted, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.str_undo){
                            saveTask(taskModel)
                        }.show()
                }
                is MainViewModel.TaskState.GetAllTask ->{
                    adapter.submitList(it.taskList)
                }
                else -> showToast(R.string.str_invalid_object)
            }
        }
    }

    inner class ItemTouchHelper(dragDirs:Int, swipeDirs:Int):
        androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when(direction){
                androidx.recyclerview.widget.ItemTouchHelper.LEFT ->{
                    viewModel.checkedTask(adapter.currentList[viewHolder.adapterPosition])
                }
                androidx.recyclerview.widget.ItemTouchHelper.RIGHT ->{
                    viewModel.unCheckedTask(adapter.currentList[viewHolder.adapterPosition])
                }
            }
        }
    }

    private fun saveTask(taskModel: TaskModel){
        viewModel.saveTask(taskModel)
    }

    private fun showToast(resString:Int){
        Toast.makeText(this, resString, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(taskModel: TaskModel?){
        val bindingDialog = FragmentDialogNewTaskDialogBinding.inflate(layoutInflater)
        bindingDialog.edtTaskName.setText(taskModel?.taskName)
        AlertDialog.Builder(this)
            .setView(bindingDialog.root)
            .setCancelable(true)
            .setTitle( if (taskModel != null) getString(R.string.str_update_task) else getString(R.string.str_new_task))
            .setNegativeButton(R.string.str_cancel, null)
            .setPositiveButton(R.string.str_save) { _: DialogInterface, _: Int ->
                val task:TaskModel = if (taskModel != null) TaskModel(taskModel.taskId, bindingDialog.edtTaskName.text.toString(), taskModel.taskCheck)
                else TaskModel(taskName = bindingDialog.edtTaskName.text.toString())
                saveTask(task)
            }.create().show()
    }
    private fun deleteTask(taskModel: TaskModel){
        viewModel.deleteTask(taskModel)
    }

    private fun editTask(taskModel: TaskModel){
        showDialog(taskModel)
    }
}