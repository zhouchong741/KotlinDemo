package com.kotlin.demo.ui.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.demo.R
import com.kotlin.demo.databinding.ActivityTestBinding
import com.kotlin.demo.extension.inflate

class TestActivity : AppCompatActivity() {

    private val viewBinding: ActivityTestBinding by inflate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.button.text = getString(R.string.app_name)
    }
}