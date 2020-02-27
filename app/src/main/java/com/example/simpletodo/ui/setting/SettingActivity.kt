package com.example.simpletodo.ui.setting


import android.os.Bundle
import android.view.MenuItem
import com.example.simpletodo.R
import com.example.simpletodo.base.BaseActivity
import com.example.simpletodo.base.SwipeDismissBaseActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : SwipeDismissBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initToolbar()

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}