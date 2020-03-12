package com.example.simpletodo.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletodo.R
import com.example.simpletodo.adapters.SwipeToDeleteCallback
import com.example.simpletodo.adapters.ToDoAdapter
import com.example.simpletodo.base.BaseFragment
import com.example.simpletodo.ui.addToDo.AddTodoFragment
import com.example.simpletodo.utils.DialogUtils
import com.example.simpletodo.utils.SnackBarUtils
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: HomeViewModel

    private lateinit var navController: NavController

    private lateinit var toDoAdapter: ToDoAdapter


    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSetting -> {
                navController.navigate(R.id.action_homeFragment_to_settingsFragment)
                true
            }
            R.id.menuAbout -> {
                navController.navigate(R.id.action_homeFragment_to_aboutFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        showToolbar()
    }

    private fun showToolbar() {
        val appCompatActivity = activity as? AppCompatActivity
        appCompatActivity?.supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        viewModel.loadData()

        viewModel.getListTodoItem().observe(viewLifecycleOwner, Observer {
            toDoAdapter.submitList(it)
        })

        viewModel.getResultStatus().observe(viewLifecycleOwner, Observer {
            context?.let {
                DialogUtils.makeSimpleDialog(
                    it,
                    getString(R.string.title_error), "Loi khong xac dinh!"
                )
            }
        })
    }

    private fun initUI() {
        navController = findNavController()

        toDoAdapter = ToDoAdapter {
            val bundle = Bundle()
            bundle.putSerializable(AddTodoFragment.EXTRA_TODO_ITEM, it)
            navController.navigate(R.id.action_homeFragment_to_addTodoFragment, bundle)
        }

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
            viewModel.deleteItem(it)
            SnackBarUtils.snackBarWithTextButton(
                mainLayout, getString(R.string.title_delete), getString(
                    R.string.title_undo
                )
            ) {
                viewModel.undoItem()
            }
        })
        itemTouchHelper.attachToRecyclerView(rvTodo)
    }


    private fun onDeleteItemError(err: Throwable) {
        context?.let {
            DialogUtils.makeSimpleDialog(it, getString(R.string.title_error), err.toString())
        }
    }
}
