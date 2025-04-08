package com.example.to_dolist_3

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {
    val tasks: LiveData<List<Task>> = dao.getAllTasks().asLiveData()

    fun addTask(title: String) {
        viewModelScope.launch {
            val task = Task(title = title, completed = false)
            dao.insert(task)
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(completed = !task.completed)
            dao.update(updatedTask)
            // Если нужно удалять выполненные задачи, раскомментируй:
            // if (updatedTask.completed) {
            //     dao.delete(updatedTask)
            // }
        }
    }
}