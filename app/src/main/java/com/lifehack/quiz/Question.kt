package com.lifehack.quiz

/**
 * Data class representing a single flashcard question.
 *
 * @param statement The hack or myth statement shown to the user
 * @param isHack    True if the statement is a real life hack, false if it is an urban myth
 * @param explanation A brief explanation shown on the review screen
 */
data class Question(
    val statement: String,
    val isHack: Boolean,
    val explanation: String
)