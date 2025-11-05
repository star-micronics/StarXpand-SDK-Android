package com.starmicronics.starxpandsdk
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linerLayout = findViewById<LinearLayout>(R.id.linearLayout)
        Util.setPadding(linerLayout)

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

        val spoolerButton = findViewById<Button>(R.id.spoolerButton)
        val spoolerIntent = Intent(this, SpoolerActivity::class.java)
        spoolerButton.setOnClickListener { startActivity(spoolerIntent) }

        val templatePrintButton = findViewById<Button>(R.id.templatePrintButton)
        val templatePrintIntent = Intent(this, TemplatePrintActivity::class.java)
        templatePrintButton.setOnClickListener { startActivity(templatePrintIntent) }

        val firmwareUpdateButton = findViewById<Button>(R.id.firmwareUpdateButton)
        val firmwareUpdateIntent = Intent(this, FirmwareUpdateActivity::class.java)
        firmwareUpdateButton.setOnClickListener { startActivity(firmwareUpdateIntent) }
    }
}