package com.example.todolist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ListarItemBinding
import com.example.todolist.model.TodoList

class TodoListAdapter : ListAdapter<TodoList, TodoListAdapter.MainViewHolder>(DiffCallBack()){

    var listenerEdit : (TodoList) -> Unit = {}
    var listenerDelete : (TodoList) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ListarItemBinding.inflate(inflate, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainViewHolder(private val binding: ListarItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(todoList: TodoList){
            binding.tvDescricao.text = todoList.titulo
            binding.tvDate.text = todoList.data
            binding.tvHora.text = todoList.hora
            binding.ivMore.setOnClickListener{
                showPooup(todoList)
            }
        }

        private fun showPooup(item: TodoList) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.pop_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionEdit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }

                return@setOnMenuItemClickListener true
            }

            popupMenu.show()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<TodoList>() {
    override fun areItemsTheSame(oldItem: TodoList, newItem: TodoList) = oldItem == newItem

    override fun areContentsTheSame(oldItem: TodoList, newItem: TodoList) = oldItem.id == newItem.id
}

