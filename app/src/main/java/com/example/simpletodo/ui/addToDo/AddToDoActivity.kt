package com.example.simpletodo.ui.addToDo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.R
import com.example.simpletodo.di.App
import kotlinx.android.synthetic.main.activity_add_to_do.*
import javax.inject.Inject


class AddToDoActivity : AppCompatActivity(), Contract.View, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    companion object {
        private const val EXTRA_TODO_ITEM = "extra_todo_item"
        fun startActivity(context: Context, toDoItem: ToDoItem? = null) {
            val intent = Intent()
            toDoItem?.let {
                intent.putExtra(EXTRA_TODO_ITEM, it)
            }
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: AddToDoPresenter

    var todoItem = ToDoItem()
    var editMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        (application as App).appComponent.addToDoActivity().inject(this)
        presenter.onAttach(this)

        if (intent.hasExtra(EXTRA_TODO_ITEM)) {
            intent.getParcelableExtra<ToDoItem>(EXTRA_TODO_ITEM)?.let {
                todoItem = it
                editMode = true
            }
        }

        ibBack.setOnClickListener(this)
        ibAdd.setOnClickListener(this)
        swRemind.setOnCheckedChangeListener(this)
    }

    override fun onUpdateDataSuccess() {

    }

    override fun onUpdateDataError(err: Throwable) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ibAdd -> {
                presenter.updateOrInsertData(todoItem, editMode)
            }
            R.id.ibBack -> {
                finish()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.swRemind -> {

            }
        }
    }

}