package com.lifehack.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lifehack.quiz.databinding.ActivityMainBinding

/**
 * MainActivity - Welcome Screen
 * Displays the app title, description, and a Start button to begin the quiz.
 */
class MainActivity : AppCompatActivity() {

    // View binding for activity_main.xml
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Welcome screen launched")

        // Navigate to QuizActivity when Start button is clicked
        binding.btnStart.setOnClickListener {
            Log.d(TAG, "Start button clicked - navigating to QuizActivity")
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}