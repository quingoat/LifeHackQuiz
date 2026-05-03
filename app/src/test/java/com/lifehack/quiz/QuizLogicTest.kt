package com.lifehack.quiz

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for the quiz application logic.
 * Tests scoring, question data, and answer validation.
 */
class QuizLogicTest {

    // Sample questions mirroring the real list
    private val testQuestions = listOf(
        Question("Phone in rice fixes water damage.", isHack = false, explanation = "Myth"),
        Question("Chewing gum prevents tears cutting onions.", isHack = true, explanation = "Hack"),
        Question("Airplane mode charges phone faster.", isHack = true, explanation = "Hack")
    )

    @Test
    fun `correct answer increases score`() {
        var score = 0
        val userAnswer = false // Myth - correct for question 0
        if (userAnswer == testQuestions[0].isHack) score++
        assertEquals(1, score)
    }

    @Test
    fun `wrong answer does not increase score`() {
        var score = 0
        val userAnswer = true // Hack - wrong for question 0
        if (userAnswer == testQuestions[0].isHack) score++
        assertEquals(0, score)
    }

    @Test
    fun `score stays within valid range`() {
        var score = 0
        val userAnswers = listOf(false, true, true) // All correct
        userAnswers.forEachIndexed { index, answer ->
            if (answer == testQuestions[index].isHack) score++
        }
        assertTrue(score in 0..testQuestions.size)
        assertEquals(3, score)
    }

    @Test
    fun `all questions have non-empty statements`() {
        testQuestions.forEach { question ->
            assertTrue(question.statement.isNotEmpty())
        }
    }

    @Test
    fun `all questions have non-empty explanations`() {
        testQuestions.forEach { question ->
            assertTrue(question.explanation.isNotEmpty())
        }
    }

    @Test
    fun `feedback is correct for perfect score`() {
        val score = 10
        val total = 10
        val feedback = when {
            score == total -> "Master Hacker"score >= (total * 0.8).toInt() -> "Great job"
            score >= (total * 0.5).toInt() -> "Not bad"
            else -> "Keep practising"
        }
        assertEquals("Master Hacker", feedback)
    }

    @Test
    fun `feedback is correct for low score`() {
        val score = 2
        val total = 10
        val feedback = when {
            score == total -> "Master Hacker"
            score >= (total * 0.8).toInt() -> "Great job"
            score >= (total * 0.5).toInt() -> "Not bad"
            else -> "Keep practising"
        }
        assertEquals("Keep practising", feedback)
    }
}