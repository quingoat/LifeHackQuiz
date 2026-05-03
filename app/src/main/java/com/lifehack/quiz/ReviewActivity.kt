package com.lifehack.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lifehack.quiz.databinding.ActivityReviewBinding

/**
 * ReviewActivity - Review Screen
 * Displays all questions along with the user's answers and the correct answers
 * with explanations, so the user can learn from mistakes.
 */
class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding

    companion object {
        private const val TAG = "ReviewActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve review data from ScoreActivity
        val reviewList = intent.getStringArrayListExtra(QuizActivity.EXTRA_ANSWERS) ?: arrayListOf()

        Log.d(TAG, "ReviewActivity showing ${reviewList.size} questions")

        // Build the review text and display it
        val reviewText = reviewList.joinToString(separator = "\n\n─────────────────────\n\n")
        binding.tvReview.text = reviewText

        // Back button
        binding.btnBack.setOnClickListener {
            Log.d(TAG, "Back button clicked")
            finish()
        }
    }
}