package com.sahmed.forecaster.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration :AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment))
        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.nav_host_fragment),appBarConfiguration!!)
    }

    override fun onSupportNavigateUp(): Boolean {
       return  NavigationUI.navigateUp(findNavController(R.id.nav_host_fragment),appBarConfiguration)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
