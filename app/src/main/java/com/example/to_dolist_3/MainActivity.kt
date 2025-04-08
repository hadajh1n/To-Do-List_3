package com.example.to_dolist_3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private val db by lazy { // Инициализация базы один раз
        Room.databaseBuilder(applicationContext, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration() // Пересоздаёт базу при несоответствии
            .build()
    }
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(db.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editInput = findViewById<EditText>(R.id.edt_input)
        val btnAdd = findViewById<Button>(R.id.btn_add)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        taskAdapter = TaskAdapter { task ->
            viewModel.toggleTaskCompletion(task)
            if (!task.completed) { // Если задача стала выполненной
                Toast.makeText(this, "Задача выполнена!", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.tasks.observe(this) { tasks ->
            taskAdapter.setTasks(tasks ?: emptyList()) // Защита от null
        }

        btnAdd.setOnClickListener {
            val title = editInput.text.toString()
            if (title.isNotEmpty()) {
                viewModel.addTask(title)
                editInput.text.clear()
            }
        }
    }
}

class TaskViewModelFactory(private val dao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(dao) as T
    }
}