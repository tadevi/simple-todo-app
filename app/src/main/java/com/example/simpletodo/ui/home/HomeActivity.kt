package com.example.simpletodo.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.R
import com.example.simpletodo.di.App
import com.example.simpletodo.ui.adapters.ToDoAdapter
import com.example.simpletodo.ui.addToDo.AddToDoActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), Contract.View {

    private val toDoAdapter = ToDoAdapter()

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.homeActivity().inject(this)

        presenter.onAttach(this)

        rvTodo.apply {
            adapter = toDoAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        fab.setOnClickListener {
            AddToDoActivity.startActivity(this)
        }
    }

    override fun onRetrieveListTodoSuccess(data: List<ToDoItem>) {
        toDoAdapter.setData(data)
    }

    override fun onRetrieveListTodoError(err: Throwable) {
        AlertDialog.Builder(this)
            .setTitle("Co loi xay ra")
            .setMessage("Load du lieu tu co so du lieu bi loi!")
            .setPositiveButton(
                android.R.string.yes
            )
            { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}