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

        resetGame(null)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    /**
     * Initializes a button in the game board and sets an OnClickListener for it.
     */
    private fun initButton(row: Int, col: Int): Button {
        val button: Button = findViewById(resources.getIdentifier("button${row}${col}", "id", packageName))
        button.setOnClickListener {
            onButtonClick(button)
        }
        return button
    }

    /**
     * Sets the button clicked to the current player's symbol, and updates the textView depending on the game's status
     */
     fun onButtonClick(view: View) {
         val button = view as Button
        if (button.text.isNotEmpty()) {
            return
        }
         button.text = playerTurn
         if (checkForWin()) {
             turnTextView.text = "Player $playerTurn wins!"
             disableButtons()
         }
         else if (checkForTie()) {
             turnTextView.text = "The Game is a Tie!"
             disableButtons()
         }
         else {
             playerTurn = if (playerTurn == "X") "O" else "X"
             turnTextView.text = "Player $playerTurn's turn"
         }
    }


    // Helper Functions

    /**
     * Checks the board for every possibility of a win using "for" loops
     * @return True if there is a win, and False if there is not a win
     */
    private fun checkForWin(): Boolean {
        // Checks for wins on rows
        for (i in 0..2) {
            if (buttons[i][0].text == buttons[i][1].text
                && buttons[i][1].text == buttons[i][2].text
                && buttons[i][0].text.isNotEmpty()) {
                return true
            }
        }

        // Checks for wins on columns
        for (i in 0..2) {
            if (buttons[0][i].text == buttons[1][i].text
                && buttons[1][i].text == buttons[2][i].text
                && buttons[0][i].text.isNotEmpty()) {
                return true
            }
        }

        // Checks for wins on diagonals
        if (buttons[0][0].text == buttons[1][1].text
            && buttons[1][1].text == buttons[2][2].text
            && buttons[0][0].text.isNotEmpty()) {
            return true
        }
        if (buttons[0][2].text == buttons[1][1].text
            && buttons[1][1].text == buttons[2][0].text
            && buttons[0][2].text.isNotEmpty()) {
            return true
        }
        return false
    }

    /**
     * Checks the board to see if all spots have either an X or an O
     * @return True if there is a tie, and False if there is not
     */
    private fun checkForTie(): Boolean {
        var count = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (buttons[i][j].text.isNotEmpty()) {
                    count++
                }
            }
        }
        return count == 9
    }

    /**
     * Disables all buttons onClick using a "for" loop
     */
    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    /**
     * Enables all buttons onClick using a "for" loop
     */
    private fun enableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = true
            }
        }
    }

    /**
     * Clears the board of all turns, and makes it player X's turn
     */
     fun resetGame(view: View?) {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
            }
        }
         enableButtons()
        playerTurn = "X"
        turnTextView.text = "Player X's Turn"
    }
}