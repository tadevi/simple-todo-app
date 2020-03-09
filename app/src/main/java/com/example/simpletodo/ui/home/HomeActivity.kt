package com.example.simpletodo.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.R
import com.example.simpletodo.adapters.SwipeToDeleteCallback
import com.example.simpletodo.adapters.ToDoAdapter
import com.example.simpletodo.base.BaseActivity
import com.example.simpletodo.di.App
import com.example.simpletodo.ui.about.AboutActivity
import com.example.simpletodo.ui.addToDo.AddToDoActivity
import com.example.simpletodo.ui.setting.SettingActivity
import com.example.simpletodo.utils.DialogUtils
import com.example.simpletodo.utils.NotificationUtils
import com.example.simpletodo.utils.SnackBarUtils
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), Contract.View {

    private val toDoAdapter = ToDoAdapter {
        AddToDoActivity.startActivity(this, it)
    }

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.homeActivity().inject(this)
        presenter.attachView(this)

        initUI()
        initToolbar()

        presenter.loadData()
    }


    private fun initUI() {
        val dividerItemDecoration = DividerItemDecoration(
            rvTodo.context,
            LinearLayoutManager.VERTICAL
        )
        rvTodo.addItemDecoration(dividerItemDecoration)
        rvTodo.apply {
            adapter = toDoAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(toDoAdapter) {
            presenter.deleteItem(it)
        })
        itemTouchHelper.attachToRecyclerView(rvTodo)

        fab.setOnClickListener {
            AddToDoActivity.startActivity(this)
        }

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSetting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            R.id.menuAbout -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRetrieveListTodoSuccess(data: List<ToDoItem>) {
        toDoAdapter.submitList(data)
    }

    override fun onRetrieveListTodoError(err: Throwable) {
        DialogUtils.makeSimpleDialog(
            this,
            "Co loi xay ra", "Loi khong xac dinh!"
        )
    }

    override fun onDeleteItemSuccess() {
        SnackBarUtils.snackBarWithTextButton(mainLayout, "Deleted", "UNDO") {
            presenter.undoItem()
        }
    }

    override fun onDeleteItemError(err: Throwable) {
        DialogUtils.makeSimpleDialog(this, "Error", err.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
