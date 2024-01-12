package com.example.room.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    val show :Boolean by tasksViewModel.showDialog.observeAsState(false)
    val text : String by tasksViewModel.myTaskDialect.observeAsState(initial = "")
    Box(Modifier.fillMaxSize()) {
        FabDialog(
            modifier = Modifier.align(Alignment.BottomEnd),
            onNewTask ={tasksViewModel.openDialog() }
        )
        AddTasksDialog(
            show = show,
            myTaskText = text,
            onClose = { tasksViewModel.closeDialog() },
            onTaskAdded = { tasksViewModel.taskCreated() },
            onTextChange = {nuevoTexto-> tasksViewModel.changeText(nuevoTexto) }
        )
    }
}

@Composable
fun FabDialog(modifier: Modifier, onNewTask: () -> Unit){
    FloatingActionButton(onClick = onNewTask,
        modifier = modifier.padding(16.dp)) {
        Icon( Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTasksDialog(
    show : Boolean,
    myTaskText:String,
    onClose: ()->Unit,
    onTaskAdded:()->Unit,
    onTextChange:(String)->Unit
){
    if (show){
        Dialog(onDismissRequest = { onClose() }) {
            Column (
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ){
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                    )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTaskText,
                    onValueChange = {nuevoTexto-> onTextChange(nuevoTexto) },
                    singleLine = true,
                    maxLines = 1)
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = onTaskAdded,
                    modifier = Modifier.fillMaxWidth()
                    ) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }

}