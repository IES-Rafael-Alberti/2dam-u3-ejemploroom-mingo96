package com.example.room.addtasks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(): ViewModel() {


    private val _showDialog =MutableLiveData<Boolean>(false)
    val showDialog:LiveData<Boolean> = _showDialog

    private val _myTaskDialect = MutableLiveData<String>("")
    val myTaskDialect:LiveData<String> = _myTaskDialect


    fun closeDialog(){
        _showDialog.value = false
    }

    fun openDialog(){
        _showDialog.value = true
    }

    fun taskCreated(){
        closeDialog()
        Log.i("dam2", _myTaskDialect.value?:"")
        _myTaskDialect.value = ""
    }

    fun changeText(newText:String){
        _myTaskDialect.value = newText
    }
}