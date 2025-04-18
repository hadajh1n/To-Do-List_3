package com.example.to_dolist_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val onTaskClicked: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var tasks: List<Task> = emptyList() // Хранит текущий список задач, который будет отображаться

    fun setTasks(tasks: List<Task>) { // Метод для обновления списка задач
        this.tasks = tasks
        notifyDataSetChanged() // Данные изменились, нужно перерисовать весь список (RecyclerView)
    }

    // Метод для создания нового элемента списка (ViewHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false) // Встроенный макет (2 текста и чекбокс)
        return TaskViewHolder(view)
    }

    // Метод для заполнения элемента данными
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    // Метод возвращает количество элементов в списке (если задачи 3, то возвращает 3)
    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitle)
        private val checkBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)

        fun bind(task: Task) {
            titleTextView.text = task.title
            checkBox.isChecked = task.completed
            checkBox.setOnClickListener { onTaskClicked(task) }
        }
    }
}