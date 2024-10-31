package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var turnTextView: TextView
    private var playerTurn =  "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        turnTextView = findViewById(R.id.textView)
        buttons = Array(3) { r ->
            Array(3) { c ->
                initButton(r, c)
            }
        }

        val newGameButton: Button = findViewById(R.id.button13)

        resetGame(null)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initButton(row: Int, col: Int): Button {
        val button: Button = findViewById(resources.getIdentifier("button${row}${col}", "id", packageName))
        button.setOnClickListener {
            onButtonClick(button)
        }
        return button
    }

     fun onButtonClick(view: View) {
         val button = view as Button
        if (button.text.isNotEmpty()) {
            return
        }
        button.text = playerTurn
        playerTurn = if (playerTurn == "X") "O" else "X"
        turnTextView.text = "Player $playerTurn's Turn"
    }

     fun resetGame(view: View?) {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
            }
        }
        playerTurn = "X"
        turnTextView.text = "Player X's Turn"
    }
}