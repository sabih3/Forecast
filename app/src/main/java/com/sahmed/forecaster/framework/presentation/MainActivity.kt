package com.sahmed.forecaster.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container,MainFragment.newInstance()).commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
