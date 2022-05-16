package com.starmicronics.starxpandsdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val discoveryButton = findViewById<Button>(R.id.discoveryButton)
        val discoveryIntent = Intent(this, DiscoveryActivity::class.java)
        discoveryButton.setOnClickListener { startActivity(discoveryIntent) }

        val printButton = findViewById<Button>(R.id.printButton)
        val printIntent = Intent(this, PrintingActivity::class.java)
        printButton.setOnClickListener { startActivity(printIntent) }

        val monitorButton = findViewById<Button>(R.id.monitorButton)
        val monitorIntent = Intent(this, MonitorActivity::class.java)
        monitorButton.setOnClickListener { startActivity(monitorIntent) }

        val statusButton = findViewById<Button>(R.id.statusButton)
        val statusIntent = Intent(this, StatusActivity::class.java)
        statusButton.setOnClickListener { startActivity(statusIntent) }
    }
}