package com.github.bspammer.klox

import com.github.bspammer.klox.TokenType.*

class Scanner(val source: String) {

    private val tokens: MutableList<Token> = mutableListOf()

    private var start: Int = 0
    private var current: Int = 0
    private var line: Int = 1

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }
        tokens.add(Token(EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        val c = source[current++]
        when (c) {
            '(' -> addToken(LEFT_PAREN)
            ')' -> addToken(RIGHT_PAREN)
            '{' -> addToken(LEFT_BRACE)
            '}' -> addToken(RIGHT_BRACE)
            ',' -> addToken(COMMA)
            '.' -> addToken(DOT)
            '-' -> addToken(MINUS)
            '+' -> addToken(PLUS)
            ';' -> addToken(SEMICOLON)
            '*' -> addToken(STAR)
            '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
            '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
            '<' -> addToken(if (match('=')) LESS_EQUAL else LESS)
            '>' -> addToken(if (match('=')) GREATER_EQUAL else GREATER)
            '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) current++;
                } else {
                    addToken(SLASH)
                }
            }
            '"' -> {
                while (peek() != '"' && !isAtEnd()) {
                    if (peek() == '\n') line++
                    current++
                }

                if (isAtEnd()) {
                    Lox.error(line, "Unterminated string.")
                    return
                }

                // The closing "
                current++

                addToken(STRING, source.substring(start + 1, current - 1))
            }


            '\n' -> line++
            // Ignore whitespace
            ' ', '\r', '\t' -> {}
            else -> Lox.error(line, "Unexpected character")
        }
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false
        current++
        return true
    }

    private fun peek(): Char {
        return if (isAtEnd()) '\u0000' else source[current]
    }

    private fun isAtEnd(): Boolean {
        return current >= source.length
    }

    private fun addToken(type: TokenType, literal: Any? = null) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

}
