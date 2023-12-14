package com.github.bspammer.klox.scanner

import com.github.bspammer.klox.scanner.TokenType.*
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
            0 1.1234 999 56789.456
            and class else false for fun if nil or print return super this true var while my_identifier orchid
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
            Token(NUMBER, "0", 0.toDouble(), 5),
            Token(NUMBER, "1.1234", "1.1234".toDouble(), 5),
            Token(NUMBER, "999", 999.toDouble(), 5),
            Token(NUMBER, "56789.456", "56789.456".toDouble(), 5),
            Token(AND, "and", null, 6),
            Token(CLASS, "class", null, 6),
            Token(ELSE, "else", null, 6),
            Token(FALSE, "false", null, 6),
            Token(FOR, "for", null, 6),
            Token(FUN, "fun", null, 6),
            Token(IF, "if", null, 6),
            Token(NIL, "nil", null, 6),
            Token(OR, "or", null, 6),
            Token(PRINT, "print", null, 6),
            Token(RETURN, "return", null, 6),
            Token(SUPER, "super", null, 6),
            Token(THIS, "this", null, 6),
            Token(TRUE, "true", null, 6),
            Token(VAR, "var", null, 6),
            Token(WHILE, "while", null, 6),
            Token(IDENTIFIER, "my_identifier", null, 6),
            Token(IDENTIFIER, "orchid", null, 6),
            Token(EOF, "", null, 6),
        ))
    }
}
