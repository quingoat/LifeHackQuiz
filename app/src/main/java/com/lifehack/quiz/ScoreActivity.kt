package com.lifehack.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lifehack.quiz.databinding.ActivityScoreBinding

/**
 * ScoreActivity - Score Screen
 * Displays the user's total correct answers, personalised feedback based on their
 * score, and a Review button to see all questions with correct answers.
 */
class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    companion object {
        private const val TAG = "ScoreActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve score data passed from QuizActivity
        val score = intent.getIntExtra(QuizActivity.EXTRA_SCORE, 0)
        val total = intent.getIntExtra(QuizActivity.EXTRA_QUESTIONS, 10)
        val reviewList = intent.getStringArrayListExtra(QuizActivity.EXTRA_ANSWERS) ?: arrayListOf()

        Log.d(TAG, "ScoreActivity received: score=$score, total=$total")

        // Display score
        binding.tvScore.text = "You scored $score out of $total"

        // Personalised feedback based on score
        val feedback = when {
            score == total -> "🏆 Perfect Score! You're a Master Hacker!"
            score >= (total * 0.😎.toInt() -> "🎉 Great job! You really know your hacks!"
            score >= (total * 0.5).toInt() -> "👍 Not bad! Keep practising!"
            else -> "💪 Keep practising! You'll spot the myths soon!"
        }
        binding.tvFeedback.text = feedback
        Log.d(TAG, "Feedback shown: $feedback")

        // Review button - show all questions and correct answers
        binding.btnReview.setOnClickListener {
            Log.d(TAG, "Review button clicked")
            val intent = Intent(this, ReviewActivity::class.java).apply {
                putStringArrayListExtra(QuizActivity.EXTRA_ANSWERS, ArrayList(reviewList))
            }
            startActivity(intent)
        }

        // Play Again button - restart the quiz from the beginning
        binding.btnPlayAgain.setOnClickListener {
            Log.d(TAG, "Play Again clicked - restarting quiz")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
