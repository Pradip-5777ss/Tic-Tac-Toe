package com.example.tictactoe

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

var playerTurn = true

class MainActivity : AppCompatActivity() {
    private lateinit var player1TextView: TextView
    private lateinit var player2TextView: TextView
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btn10: Button
    private var player1Count = 0
    private var player2Count = 0
    private var player1 = ArrayList<Int>()
    private var player2 = ArrayList<Int>()
    private var emptyCells = ArrayList<Int>()
    private var activeUser = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player1TextView = findViewById(R.id.textView1)
        player2TextView = findViewById(R.id.textView2)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        btn4 = findViewById(R.id.button4)
        btn5 = findViewById(R.id.button5)
        btn6 = findViewById(R.id.button6)
        btn7 = findViewById(R.id.button7)
        btn8 = findViewById(R.id.button8)
        btn9 = findViewById(R.id.button9)
        btn10 = findViewById(R.id.button10)

        btn10.setOnClickListener {
            reset()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9) {
            val buttonSelected: Button = when (i) {
                1 -> btn1
                2 -> btn2
                3 -> btn3
                4 -> btn4
                5 -> btn5
                6 -> btn6
                7 -> btn7
                8 -> btn8
                9 -> btn9
                else -> {
                    btn1
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = ""
            player1TextView.text = "player1 : $player1Count"
            player2TextView.text = "player2 : $player2Count"

        }
    }

    fun btnClick(view: View) {
        if (playerTurn) {
            val butt = view as Button
            var cellID = 0
            when (butt.id) {
                R.id.button1 -> cellID = 1
                R.id.button2 -> cellID = 2
                R.id.button3 -> cellID = 3
                R.id.button4 -> cellID = 4
                R.id.button5 -> cellID = 5
                R.id.button6 -> cellID = 6
                R.id.button7 -> cellID = 7
                R.id.button8 -> cellID = 8
                R.id.button9 -> cellID = 9
            }

            playerTurn = false
            Handler(Looper.getMainLooper()).postDelayed({ playerTurn = true }, 600)
            playNow(butt, cellID)
        }
    }

    private fun playNow(buttonSelected: Button, currentCell: Int) {
        val audio = MediaPlayer.create(applicationContext, R.raw.single_tap)
        if (activeUser == 1) {
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
            player1.add(currentCell)
            emptyCells.add(currentCell)
            audio.start()
            buttonSelected.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({ audio.release() }, 200)

            val checkWinner = checkWinner()

            when {
                checkWinner == 1 -> {
                    Handler(Looper.getMainLooper()).postDelayed({ reset() }, 200)
                }
                singleUser -> {
                    Handler(Looper.getMainLooper()).postDelayed({ robot() }, 500)
                }
                else -> {
                    activeUser = 2
                }
            }
        } else {
            buttonSelected.text = "O"
            audio.start()
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            activeUser = 1
            player2.add(currentCell)
            emptyCells.add(currentCell)
            Handler(Looper.getMainLooper()).postDelayed({ audio.release() }, 200)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler(Looper.getMainLooper()).postDelayed({ reset() }, 4000)
            }
        }
    }

    private fun robot() {
        val random = (1..9).random()
        if (emptyCells.contains(random)) {
            robot()
        } else {
            val buttonSelected: Button = when (random) {
                1 -> btn1
                2 -> btn2
                3 -> btn3
                4 -> btn4
                5 -> btn5
                6 -> btn6
                7 -> btn7
                8 -> btn8
                9 -> btn9
                else -> {
                    btn1
                }
            }
            emptyCells.add(random)
            val audio = MediaPlayer.create(this, R.raw.single_tap)
            audio.start()
            Handler(Looper.getMainLooper()).postDelayed({ audio.release() }, 500)
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            player2.add(random)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler(Looper.getMainLooper()).postDelayed({ reset() }, 2000)
            }

        }
    }

    private fun checkWinner(): Int {
        val audio = MediaPlayer.create(this, R.raw.victory)
        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(3) && player1.contains(5) && player1.contains(9))
        ) {
            player1Count += 1
            buttonDisable()
            audio.start()
            disableReset()
            Handler(Looper.getMainLooper()).postDelayed({ audio.release() }, 4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 Wins!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("OK") { _, _ ->
                reset()
                audio.release()
            }

            build.setNegativeButton("Exit") { _, _ ->
                audio.release()
                exitProcess(1)
            }

            Handler(Looper.getMainLooper()).postDelayed({ build.show() }, 2000)
            return 1
        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(9))
        ) {
            player2Count += 1
            audio.start()
            buttonDisable()
            disableReset()
            Handler(Looper.getMainLooper()).postDelayed({ audio.release() }, 4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 Wins!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("OK") { _, _ ->
                reset()
                audio.release()
            }

            build.setNegativeButton("Exit") { _, _ ->
                audio.release()
                exitProcess(1)
            }

            Handler(Looper.getMainLooper()).postDelayed({ build.show() }, 2000)
            return 1
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) &&
            emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6) &&
            emptyCells.contains(7) && emptyCells.contains(8) && emptyCells.contains(9)
        ) {

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Draw")
            build.setMessage("Nobody Wins!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("OK") { _, _ ->
                reset()
            }

            build.setNegativeButton("Exit") { _, _ ->
                exitProcess(1)
            }

            build.show()
            return 1
        }
        return 0
    }

    private fun disableReset() {
        btn10.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({ btn10.isEnabled = true }, 2200)
    }

    @SuppressLint("SetTextI18n")
    private fun buttonDisable() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9) {
            val buttonSelected: Button = when (i) {

                1 -> btn1
                2 -> btn2
                3 -> btn3
                4 -> btn4
                5 -> btn5
                6 -> btn6
                7 -> btn7
                8 -> btn8
                9 -> btn9
                else -> {
                    btn1
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = ""
            player1TextView.text = "Player 1 : $player1Count"
            player2TextView.text = "Player 2 : $player2Count"
        }
    }
}