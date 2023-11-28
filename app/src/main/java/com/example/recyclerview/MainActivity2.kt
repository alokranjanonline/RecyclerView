package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var receiver_msg: TextView = findViewById(R.id.txtSchemename)
        val intet: Intent
        val str = intent.getStringExtra("message_key")
        receiver_msg.text=str
    }
}