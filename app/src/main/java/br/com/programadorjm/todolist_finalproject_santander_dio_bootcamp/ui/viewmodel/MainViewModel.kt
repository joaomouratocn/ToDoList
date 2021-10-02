package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.ui.viewmodel

import androidx.lifecycle.*
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.RepositoryImpl
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.model.TaskModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(private val repositoryImpl: RepositoryImpl) : ViewModel() {
    private val taskState = MutableLiveData<TaskState>()
    val taskStateView:LiveData<TaskState> = taskState

    init {
        getAllTask()
    }

    sealed class TaskState{
        class GetAllTask(val taskList: List<TaskModel>):TaskState()
        class DeleteSuccess(val taskModel: TaskModel):TaskState()

        object SaveSuccess:TaskState()
        object UpdateSuccess:TaskState()
        object InvalidTaskName:TaskState()
        object TaskIsChecked:TaskState()
        object TaskIsUnChecked:TaskState()
    }

    private fun getAllTask(){
        viewModelScope.launch {
            taskState.value = TaskState.GetAllTask(repositoryImpl.getAllTask())
        }
    }

    fun saveTask(taskModel: TaskModel) {
        if(taskModel.taskName.isEmpty()){ taskState.value = TaskState.InvalidTaskName}
        else{
            viewModelScope.launch {
                repositoryImpl.saveTask(taskModel)
                taskState.value = TaskState.GetAllTask(repositoryImpl.getAllTask())
            }
            taskState.value = TaskState.SaveSuccess
        }
    }

    fun updateTask(taskName: String){
        val taskModel = TaskModel(taskName = taskName)
        viewModelScope.launch {
            repositoryImpl.updateTask(taskModel)
            taskState.value = TaskState.GetAllTask(repositoryImpl.getAllTask())
        }
        taskState.value = TaskState.UpdateSuccess
    }

    fun checkedTask(taskModel:TaskModel){
        if(taskModel.taskCheck)taskState.value = TaskState.TaskIsChecked
        else{
            val task = TaskModel(taskModel.taskId , taskModel.taskName, true)
            viewModelScope.launch {
                repositoryImpl.updateTask(task)
                taskState.value = TaskState.GetAllTask(repositoryImpl.getAllTask())
            }
            taskState.value = TaskState.UpdateSuccess
        }
    }

    fun unCheckedTask(taskModel: TaskModel) {
        if (!taskModel.taskCheck)taskState.value = TaskState.TaskIsUnChecked
        else{
            val task = TaskModel(taskModel.taskId, taskModel.taskName, false)
            viewModelScope.launch {
                repositoryImpl.updateTask(task)
                taskState.value = TaskState.GetAllTask(repositoryImpl.getAllTask())
            }
        }
        taskState.value = TaskState.TaskIsUnChecked
    }

    fun deleteTask(taskModel: TaskModel){
        viewModelScope.launch {
            repositoryImpl.deleteTask(taskModel)
            taskState.value = TaskState.GetAllTask(repositoryImpl.getAllTask())
        }
        taskState.value = TaskState.DeleteSuccess(taskModel)
    }

    class ViewModelFactory(private val repositoryImpl: RepositoryImpl): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                return modelClass.getConstructor(RepositoryImpl::class.java).newInstance(repositoryImpl)
            }
            throw IllegalArgumentException("Unknown class")
        }
    }
}