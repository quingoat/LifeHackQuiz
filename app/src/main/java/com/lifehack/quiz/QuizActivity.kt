package com.lifehack.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lifehack.quiz.databinding.ActivityQuizBinding

/**
 * QuizActivity - Flashcard Question Screen
 * Loops through all questions, accepts user answers, shows feedback,
 * tracks the score, and navigates to the Score screen when done.
 */
class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    companion object {
        private const val TAG = "QuizActivity"
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_QUESTIONS = "extra_questions"
        const val EXTRA_ANSWERS = "extra_answers"
    }

    // Full list of Life Hack or Urban Myth questions
    private val questions = listOf(
        Question(
            statement = "Putting your phone in rice will fix water damage.",
            isHack = false,
            explanation = "Urban Myth! Rice is not effective at drying electronics. Silica gel or professional repair is better."
        ),
        Question(
            statement = "Chewing gum while cutting onions prevents tears.",
            isHack = true,
            explanation = "Life Hack! Chewing gum encourages you to breathe through your mouth, reducing exposure to irritating vapours."
        ),
        Question(
            statement = "You can charge your phone faster in airplane mode.",
            isHack = true,
            explanation = "Life Hack! Airplane mode disables background processes that consume power, making charging faster."
        ),
        Question(
            statement = "Drinking coffee sobers you up after alcohol.",
            isHack = false,
            explanation = "Urban Myth! Caffeine makes you feel more alert but does not reduce your blood alcohol level."
        ),
        Question(
            statement = "Placing a wooden spoon over a boiling pot stops it from boiling over.",
            isHack = true,
            explanation = "Life Hack! The wooden spoon disrupts surface tension of bubbles, preventing overflow."
        ),
        Question(
            statement = "Reading in dim light permanently damages your eyesight.",
            isHack = false,
            explanation = "Urban Myth! It may cause eye strain but causes no permanent damage to your vision."
        ),
        Question(
            statement = "Using the incognito/private tab prevents websites from tracking you.",
            isHack = false,
            explanation = "Urban Myth! Incognito only prevents local history. Your ISP and websites can still track you."
        ),
        Question(
            statement = "A banana peel can soothe an insect bite or minor skin irritation.",
            isHack = true,
            explanation = "Life Hack! Banana peels contain compounds like polyphenols that have mild anti-inflammatory effects."
        ),
        Question(
            statement = "Shaving makes hair grow back thicker.",
            isHack = false,
            explanation = "Urban Myth! Shaving only cuts the tip of the hair. It does not affect the follicle or thickness."
        ),
        Question(
            statement = "Freezing a battery briefly can extend its life.",
            isHack = false,
            explanation = "Urban Myth! Freezing modern lithium batteries can cause condensation and permanent damage."
        )
    )

    // Tracks the index of the current question
    private var currentIndex = 0

    // Tracks the number of correct answers
    private var score = 0

    // Stores the user's answers (true = Hack, false = Myth) for review
    private val userAnswers = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "QuizActivity started with ${questions.size}questions")

        // Display the first question
        displayQuestion()

        // Handle "Hack" button click
        binding.btnHack.setOnClickListener {
            Log.d(TAG, "User selected: Hack for question $currentIndex")
            handleAnswer(userSelectedHack = true)
        }

        // Handle "Myth" button click
        binding.btnMyth.setOnClickListener {
            Log.d(TAG, "User selected: Myth for question $currentIndex")
            handleAnswer(userSelectedHack = false)
        }

        // Handle "Next" button click
        binding.btnNext.setOnClickListener {
            currentIndex++
            Log.d(TAG, "Moving to question $currentIndex")

            if (currentIndex < questions.size) {
                displayQuestion()
            } else {
                // All questions answered - go to Score screen
                Log.d(TAG, "Quiz complete. Score: $score / ${questions.size}")
                navigateToScoreScreen()
            }
        }
    }/**
     * Displays the current question and resets UI state.
     */
    private fun displayQuestion() {
        val question = questions[currentIndex]

        // Update question counter and statement
        binding.tvQuestionNumber.text = "Question ${currentIndex + 1} of ${questions.size}"
        binding.tvStatement.text = question.statement

        // Hide feedback and Next button until user answers
        binding.tvFeedback.visibility = View.INVISIBLE
        binding.btnNext.visibility = View.INVISIBLE

        // Re-enable answer buttons
        binding.btnHack.isEnabled = true
        binding.btnMyth.isEnabled = true

        Log.d(TAG, "Displaying question ${currentIndex + 1}: ${question.statement}")
    }

    /**
     * Checks the user's answer, updates score, shows feedback, and records the answer.
     *
     * @param userSelectedHack True if user pressed "Hack", false if "Myth"
     */
    private fun handleAnswer(userSelectedHack: Boolean) {
        val question = questions[currentIndex]
        val isCorrect = userSelectedHack == question.isHack

        // Record the user's answer for the review screen
        userAnswers.add(userSelectedHack)

        // Update score if correct
        if (isCorrect) {
            score++
            binding.tvFeedback.text = "✅ Correct! That's a real time-saver!"
            Log.d(TAG, "Correct answer for question $currentIndex")
        } else {
            binding.tvFeedback.text = "❌ Wrong! That's just an urban myth."
            Log.d(TAG, "Incorrect answer for question $currentIndex")
        }

        // Show feedback and Next button, disable answer buttons
        binding.tvFeedback.visibility = View.VISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.btnHack.isEnabled = false
        binding.btnMyth.isEnabled = false
    }

    /**
     * Passes the score and question/answer data to ScoreActivity.
     */
    private fun navigateToScoreScreen() {
        val intent = Intent(this,ScoreActivity::class.java).apply {
            putExtra(EXTRA_SCORE, score)
            putExtra(EXTRA_QUESTIONS, questions.size)
            // Pass statements and correct answers as arrays for the review screen
            putStringArrayListExtra(
                EXTRA_ANSWERS,
                ArrayList(questions.mapIndexed { index, q ->
                    val userAnswer = if (userAnswers[index]) "Hack" else "Myth"
                    val correctAnswer = if (q.isHack) "Hack" else "Myth"
                    val correct = if (userAnswers[index] == q.isHack) "✅" else "❌"
                    "$correct  ${q.statement}\nYour answer: $userAnswer | Correct: $correctAnswer\n${q.explanation}"
                })
            )
        }
        startActivity(intent)
        finish()
    }
}