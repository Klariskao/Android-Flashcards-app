package com.example.flashcards.data.model

enum class Category(
    val displayName: String,
) {
    ANIMALS("Animals"),
    COLORS("Colors"),
    FOOD("Food"),
    NATURE("Nature"),
    TRANSPORT("Transport"),
    VERBS("Verbs"),
    UNKNOWN("Unknown"),
    ;

    companion object {
        fun fromString(value: String): Category? = entries.find { it.name == value }
    }
}
