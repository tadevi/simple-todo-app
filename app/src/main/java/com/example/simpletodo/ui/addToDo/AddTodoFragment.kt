package com.example.simpletodo.ui.addToDo

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.R
import com.example.simpletodo.base.BaseFragment
import com.example.simpletodo.utils.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_add_to_do.*
import org.koin.android.ext.android.get
import java.util.*
import javax.inject.Inject


class AddTodoFragment : BaseFragment(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    companion object {
        const val EXTRA_TODO_ITEM = "extra_todo_item"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: AddTodoViewModel

    private var todoItem = ToDoItem()
    private var editMode = false
    private var timeRemind: Calendar = Calendar.getInstance()


    override fun getLayoutId(): Int {
        return R.layout.fragment_add_to_do
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViewModel()
        registerCallback()
        hideToolbar()
    }

    fun hideToolbar() {
        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.apply {
            this.hide()
        }
    }

    private fun registerCallback() {
        ibBack.setOnClickListener(this)
        ibAdd.setOnClickListener(this)
        tvDate.setOnClickListener(this)
        tvTime.setOnClickListener(this)
        swRemind.setOnCheckedChangeListener(this)
    }

    private fun initData() {
        arguments?.let { argument ->
            argument.getSerializable(EXTRA_TODO_ITEM)?.let { extraItem ->
                todoItem = extraItem as ToDoItem
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

        context?.let {
            val typeface = Typeface.createFromAsset(it.assets, "fonts/Aller_Regular.ttf")
            etName.typeface = typeface
            etDesc.typeface = typeface
            tvDate.typeface = typeface
            tvTime.typeface = typeface
            tvAdditional.typeface = typeface
        }
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            factory
        ).get(AddTodoViewModel::class.java)
        viewModel.getResult().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                when (it) {
                    is Result.Success -> onUpdateDataSuccess(it.item)
                    is Result.Error -> onUpdateDataError(it.e)
                }
            }
        })
    }

    private fun onUpdateDataSuccess(item: ToDoItem) {
        subscribeToAlarmManager(item)
        findNavController().popBackStack()
    }

    private fun onUpdateDataError(err: Throwable) {
        context?.let {
            DialogUtils.makeSimpleDialog(
                it,
                getString(R.string.title_error),
                getString(R.string.message_error_name_must_not_empty_and_unique)
            )
        }
    }

    private fun subscribeToAlarmManager(toDoItem: ToDoItem) {
        context?.let {
            AlarmUtils.updateAlarm(it, todoItem)
        }
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
                    viewModel.updateOrInsertData(todoItem, editMode)
                } else {
                    findNavController().popBackStack()
                    return
                }
            }
            R.id.ibBack -> {
                findNavController().popBackStack()
            }
            R.id.tvDate -> {
                (activity as? AppCompatActivity)?.showDatePickerDialog(this, todoItem.datetime)
            }
            R.id.tvTime -> {
                (activity as? AppCompatActivity)?.showTimePickerDialog(this, todoItem.datetime)
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
            context?.let {
                DialogUtils.makeSimpleDialog(
                    it,
                    getString(R.string.title_error),
                    getString(R.string.message_invalid_date_in_past)
                )
            }
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
            context?.let {
                DialogUtils.makeSimpleDialog(
                    it,
                    getString(R.string.title_error),
                    getString(R.string.message_invalid_date_in_past)
                )
            }
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