package com.example.todolist.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.AddActivityBinding
import com.example.todolist.datasource.TodoListDataSource
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.TodoList
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*

class CreateActivity : AppCompatActivity() {

    private lateinit var binding: AddActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TODOLIST_ID)) {
            val todoListId = intent.getIntExtra(TODOLIST_ID, 0)
            TodoListDataSource.findById(todoListId)?.let {
                binding.txtTitle.text = it.titulo
                binding.txtData.text = it.data
                binding.txtHora.text = it.hora
            }
        }

        insertListeners()

    }


    private fun insertListeners() {
        binding.txtData.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
               binding.txtData.editText?.setText(Date(it + offSet).format())
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.txtHora.editText?.setOnClickListener {
            val picker = MaterialTimePicker.Builder().build()
            picker.addOnPositiveButtonClickListener {
                val minute = if (picker.minute in 0..9) "0${picker.minute}" else picker.minute
                val hour = if (picker.hour in 0..9) "0${picker.hour}" else picker.hour
                binding.txtHora.editText?.setText("${hour} : ${minute}")
            }
            picker.show(supportFragmentManager, null)
        }

        binding.btnCancelTask.setOnClickListener {
            finish()
        }

        binding.btnAddTask.setOnClickListener {
            val todoList = TodoList(
                titulo = binding.txtTitle.text,
                data = binding.txtData.text,
                hora = binding.txtHora.text,
                id = intent.getIntExtra(TODOLIST_ID, 0)
            )
             TodoListDataSource.insertTodolist(todoList)
            setResult(Activity.RESULT_OK)
            finish()

        }
    }

    companion object {
        const val TODOLIST_ID = "todo_list"
    }
}