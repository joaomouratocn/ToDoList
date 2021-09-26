package br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.programadorjm.todolist_finalproject_santander_dio_bootcamp.data.RepositoryImpl
import java.lang.IllegalArgumentException

class MainViewModel(private val repositoryImpl: RepositoryImpl) : ViewModel() {


    class ViewModelFactory(private val repositoryImpl: RepositoryImpl): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                return modelClass.getConstructor(RepositoryImpl::class.java).newInstance(repositoryImpl)
            }
            throw IllegalArgumentException("Unknown class")
        }
    }
}