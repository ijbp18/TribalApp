package com.home.tribalapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.home.tribalapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configNav()
        setSupportActionBar(toolbar_main)
    }

    private fun configNav() = Navigation.findNavController(this, R.id.nav_host_fragment)
}