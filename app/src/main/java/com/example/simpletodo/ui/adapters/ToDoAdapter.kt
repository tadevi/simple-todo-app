package com.example.simpletodo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.R
import java.text.SimpleDateFormat

class ToDoAdapter : RecyclerView.Adapter<ToDoViewHolder>() {
    private val todos = ArrayList<ToDoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder.newInstance(parent)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    fun setData(data: List<ToDoItem>) {
        todos.clear()
        todos.addAll(data)
        notifyDataSetChanged()
    }
}

class ToDoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun newInstance(parent: ViewGroup): ToDoViewHolder {
            return ToDoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.to_do_item,
                    parent,
                    false
                )
            )
        }
    }

    private val tvName: TextView by lazy {
        view.findViewById<TextView>(R.id.tvName)
    }
    private val tvTime: TextView by lazy {
        view.findViewById<TextView>(R.id.tvTime)
    }
    private val tvDesc: TextView by lazy {
        view.findViewById<TextView>(R.id.tvDesc)
    }
    private val tvAvatar: TextView by lazy {
        view.findViewById<TextView>(R.id.tvAvatar)
    }

    fun bind(todo: ToDoItem) {
        tvName.text = todo.name
        tvDesc.text = todo.description
        tvTime.text = SimpleDateFormat("dd/mm/yyyy hh:mm").format(todo.datetime)
        tvAvatar.text = todo.name.first().toUpperCase().toString()
    }
}