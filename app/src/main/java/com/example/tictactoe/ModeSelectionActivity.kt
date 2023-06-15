package com.example.tictactoe

import android.content.Intent
import android.net.MailTo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

var singleUser = false
class ModeSelectionActivity : AppCompatActivity() {

    lateinit var singlePlayerButton: Button
    lateinit var multiPlayerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_selection)

        singlePlayerButton = findViewById(R.id.single_player)
        multiPlayerButton = findViewById(R.id.Multi_player)

        singlePlayerButton.setOnClickListener {
            singleUser = true
            startActivity(Intent(this, MainActivity::class.java))
        }

        multiPlayerButton.setOnClickListener {
            singleUser = false
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}