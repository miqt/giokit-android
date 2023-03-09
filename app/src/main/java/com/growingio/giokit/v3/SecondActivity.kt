package com.growingio.giokit.v3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.dians.stc.StcDians

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.track)
        tv.text = "custom track"
        tv.setOnClickListener {
            StcDians.track(applicationContext,"customEvent")
            startActivity(Intent(this, MainActivity::class.java))
        }

        val webBtn = findViewById<Button>(R.id.web)
        webBtn.setOnClickListener { startActivity(Intent(this, WebCircleHybridActivity::class.java)) }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    fun justislongmethod() {
        StcDians.track(applicationContext,"customEvent")
    }
}