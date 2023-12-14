package com.github.bspammer.klox.scanner

import com.github.bspammer.klox.Lox
import com.github.bspammer.klox.scanner.TokenType.*

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
        when {
            c == '(' -> addToken(LEFT_PAREN)
            c == ')' -> addToken(RIGHT_PAREN)
            c == '{' -> addToken(LEFT_BRACE)
            c == '}' -> addToken(RIGHT_BRACE)
            c == ',' -> addToken(COMMA)
            c == '.' -> addToken(DOT)
            c == '-' -> addToken(MINUS)
            c == '+' -> addToken(PLUS)
            c == ';' -> addToken(SEMICOLON)
            c == '*' -> addToken(STAR)
            c == '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
            c == '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
            c == '<' -> addToken(if (match('=')) LESS_EQUAL else LESS)
            c == '>' -> addToken(if (match('=')) GREATER_EQUAL else GREATER)
            c == '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) current++;
                } else {
                    addToken(SLASH)
                }
            }
            c == '"' -> {
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
            c.isDigit() -> {
                while (peek().isDigit()) current++

                if (peek() == '.' && peek(1).isDigit()) {
                    current++
                    while (peek().isDigit()) current++
                }

                addToken(NUMBER, source.substring(start, current).toDouble())
            }
            c.isLetter() || c == '_' -> {
                while (peek().isLetter() || peek() == '_') current++
                val type = mapOf(
                    "and" to AND,
                    "class" to CLASS,
                    "else" to ELSE,
                    "false" to FALSE,
                    "for" to FOR,
                    "fun" to FUN,
                    "if" to IF,
                    "nil" to NIL,
                    "or" to OR,
                    "print" to PRINT,
                    "return" to RETURN,
                    "super" to SUPER,
                    "this" to THIS,
                    "true" to TRUE,
                    "var" to VAR,
                    "while" to WHILE,
                )[source.substring(start, current)] ?: IDENTIFIER
                addToken(type)
            }


            c == '\n' -> line++
            // Ignore whitespace
            c == ' ' || c == '\r' || c == '\t' -> {}
            else -> Lox.error(line, "Unexpected character")
        }
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false
        current++
        return true
    }

    private fun peek(ahead: Int = 0): Char {
        return if (isAtEnd()) '\u0000' else source[current + ahead]
    }

    private fun isAtEnd(): Boolean {
        return current >= source.length
    }

    private fun addToken(type: TokenType, literal: Any? = null) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

}
