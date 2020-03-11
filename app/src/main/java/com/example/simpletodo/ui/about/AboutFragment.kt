package com.example.simpletodo.ui.about

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import com.example.simpletodo.R
import com.example.simpletodo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_about
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        context?.let {
            tvAboutDesc.text =
                HtmlCompat.fromHtml(it.getString(R.string.about), HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    }
}