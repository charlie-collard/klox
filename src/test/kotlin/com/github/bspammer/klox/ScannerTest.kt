package com.github.bspammer.klox

import com.github.bspammer.klox.TokenType.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ScannerTest {

    @Test
    fun scanTokens() {
        val scanner = Scanner("""
            // this is a comment
            (( )){} // grouping stuff
            !*+-/=<> <= == // operators
            "this is a string"
        """.trimIndent())
        val tokens = scanner.scanTokens()

        assertThat(tokens).isEqualTo(listOf(
            Token(LEFT_PAREN, "(", null, 2),
            Token(LEFT_PAREN, "(", null, 2),
            Token(RIGHT_PAREN, ")", null, 2),
            Token(RIGHT_PAREN, ")", null, 2),
            Token(LEFT_BRACE, "{", null, 2),
            Token(RIGHT_BRACE, "}", null, 2),
            Token(BANG, "!", null, 3),
            Token(STAR, "*", null, 3),
            Token(PLUS, "+", null, 3),
            Token(MINUS, "-", null, 3),
            Token(SLASH, "/", null, 3),
            Token(EQUAL, "=", null, 3),
            Token(LESS, "<", null, 3),
            Token(GREATER, ">", null, 3),
            Token(LESS_EQUAL, "<=", null, 3),
            Token(EQUAL_EQUAL, "==", null, 3),
            Token(STRING, "\"this is a string\"", "this is a string", 4),
            Token(EOF, "", null, 4),
        ))
    }
}
