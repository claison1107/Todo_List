package com.example.todolist.datasource

import com.example.todolist.model.TodoList

object TodoListDataSource {

    private val list = arrayListOf<TodoList>()

    fun getList() = list.toList()

    fun insertTodolist(todoList: TodoList) {
        if (todoList.id == 0) {
            list.add(todoList.copy(id = list.size + 1))
        }else {
            list.remove(todoList)
            list.add(todoList)
        }

    }

    fun findById(todoListId: Int) = list.find { it.id == todoListId }

    fun deleteTodoList(todoList: TodoList) {
        list.remove(todoList)
    }

}