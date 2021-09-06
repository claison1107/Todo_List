package com.example.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.datasource.TodoListDataSource
import com.example.todolist.ui.adapter.TodoListAdapter


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TodoListAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        updateTodoList()

        insertListener()
    }

    private fun insertListener() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, CreateActivity::class.java), CREATE_TODO_LIST)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, CreateActivity::class.java)
            intent.putExtra(CreateActivity.TODOLIST_ID, it.id)
            startActivityForResult(intent, CREATE_TODO_LIST)
        }

        adapter.listenerDelete = {
            TodoListDataSource.deleteTodoList(it)
                updateTodoList()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_TODO_LIST && resultCode == Activity.RESULT_OK) updateTodoList()
    }

    private fun updateTodoList() {
        adapter.submitList(TodoListDataSource.getList())
    }
    companion object {
        private const val CREATE_TODO_LIST = 1000
    }
}
