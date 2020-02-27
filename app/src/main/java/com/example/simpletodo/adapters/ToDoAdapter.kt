package com.example.simpletodo.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.R
import com.example.simpletodo.utils.formatDate
import java.util.*


class ToDoAdapter(
    private val onClick: ((ToDoItem) -> Unit)? = null
) :
    ListAdapter<ToDoItem, ToDoViewHolder>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ToDoItem>() {
            override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

class ToDoViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {
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

    init {
        val typeface = Typeface.createFromAsset(view.context.assets, "fonts/Aller_Regular.ttf")
        tvAvatar.typeface = typeface
        tvTime.typeface = typeface
        tvDesc.typeface = typeface
        tvName.typeface = typeface
    }

    private fun makeRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    fun bind(todo: ToDoItem, onClick: ((ToDoItem) -> Unit)?) {
        tvName.text = todo.name
        tvDesc.text = todo.description
        tvTime.text = todo.datetime?.let {
            formatDate(it)
        }
        tvAvatar.text = todo.name.firstOrNull()?.let {
            it.toUpperCase().toString()
        }

        val drawable =
            ContextCompat.getDrawable(view.context, R.drawable.circle) as? GradientDrawable

        val color = makeRandomColor()
        drawable?.setColor(color)
        drawable?.let {
            tvAvatar.background = it
        }
        view.setOnClickListener {
            onClick?.invoke(todo)
        }
    }
}