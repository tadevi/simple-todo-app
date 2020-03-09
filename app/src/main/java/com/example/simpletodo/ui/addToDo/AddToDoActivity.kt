package com.example.simpletodo.ui.addToDo

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.R
import com.example.simpletodo.base.BaseActivity
import com.example.simpletodo.di.App
import com.example.simpletodo.utils.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_add_to_do.*
import java.util.*
import javax.inject.Inject


class AddToDoActivity : BaseActivity(), Contract.View, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    companion object {
        private const val EXTRA_TODO_ITEM = "extra_todo_item"
        fun startActivity(context: Context, toDoItem: ToDoItem? = null, flag: Int? = null) {
            val intent = Intent(context, AddToDoActivity::class.java)
            intent.flags = flag ?: Intent.FLAG_ACTIVITY_NEW_TASK
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
    var timeRemind: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        (application as App).appComponent.addToDoActivity().inject(this)
        presenter.attachView(this)

        registerCallback()
        initData()
    }

    private fun registerCallback() {
        ibBack.setOnClickListener(this)
        ibAdd.setOnClickListener(this)
        tvDate.setOnClickListener(this)
        tvTime.setOnClickListener(this)
        swRemind.setOnCheckedChangeListener(this)
    }

    private fun initData() {
        if (intent.hasExtra(EXTRA_TODO_ITEM)) {
            intent.getSerializableExtra(EXTRA_TODO_ITEM)?.let {
                todoItem = it as ToDoItem
                editMode = true
            }
        }
        swRemind.isChecked = !todoItem.isFinish
        etName.setText(todoItem.name)
        etDesc.setText(todoItem.description)
        val calendar = Calendar.getInstance()
        tvTime.text = formatTime(todoItem.datetime ?: calendar)
        tvDate.text = formatDate(todoItem.datetime ?: calendar)
        setVisibilityDateTime()
        etName.requestFocus()

        val typeface = Typeface.createFromAsset(assets, "fonts/Aller_Regular.ttf")
        etName.typeface = typeface
        etDesc.typeface = typeface
        tvDate.typeface = typeface
        tvTime.typeface = typeface
        tvAdditional.typeface = typeface
        
    }

    override fun onUpdateDataSuccess() {
        finish()
    }

    override fun onUpdateDataError(err: Throwable) {
        DialogUtils.makeSimpleDialog(
            this,
            getString(R.string.title_error),
            getString(R.string.message_error_name_must_not_empty_and_unique)
        )
    }

    override fun subscribeToAlarmManager(toDoItem: ToDoItem) {
        AlarmUtils.updateAlarm(this, todoItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ibAdd -> {
                todoItem = todoItem.copy(
                    name = etName.text.toString(),
                    description = etDesc.text.toString(),
                    datetime = if (swRemind.isChecked) timeRemind else null,
                    isFinish = !swRemind.isChecked
                )
                if (todoItem.name.isNotEmpty()) {
                    presenter.updateOrInsertData(todoItem, editMode)
                } else {
                    finish()
                    return
                }
            }
            R.id.ibBack -> {
                finish()
            }
            R.id.tvDate -> {
                showDatePickerDialog(this, todoItem.datetime)
            }
            R.id.tvTime -> {
                showTimePickerDialog(this, todoItem.datetime)
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.swRemind -> {
                setVisibilityDateTime()
                todoItem = todoItem.copy(isFinish = !isChecked)
            }
        }
    }

    private fun setVisibilityDateTime() {
        if (swRemind.isChecked) {
            tvDate.visibility = View.VISIBLE
            tvTime.visibility = View.VISIBLE
            tvAdditional.visibility = View.VISIBLE
        } else {
            tvDate.visibility = View.GONE
            tvTime.visibility = View.GONE
            tvAdditional.visibility = View.GONE
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val now = Calendar.getInstance()

        if (year < now.get(Calendar.YEAR)
            || monthOfYear < now.get(Calendar.MONTH)
            || dayOfMonth < now.get(Calendar.DAY_OF_MONTH)
        ) {
            DialogUtils.makeSimpleDialog(
                this,
                getString(R.string.title_error),
                getString(R.string.message_invalid_date_in_past)
            )
        } else {
            timeRemind.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            timeRemind.let {
                tvDate.text = formatDate(it)
                tvAdditional.text = resources.getString(
                    R.string.add_to_do_additional_reminded, formatDate(it),
                    formatTime(it)
                )
            }
        }
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        val now = Calendar.getInstance()
        if (hourOfDay < now.get(Calendar.HOUR_OF_DAY) || minute < now.get(Calendar.MINUTE)) {
            DialogUtils.makeSimpleDialog(
                this,
                getString(R.string.title_error),
                getString(R.string.message_invalid_date_in_past)
            )
        } else {
            timeRemind.apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }

            timeRemind.let {
                tvTime.text = formatTime(it)
                tvAdditional.text = resources.getString(
                    R.string.add_to_do_additional_reminded, formatDate(it),
                    formatTime(it)
                )
            }
        }
    }
}