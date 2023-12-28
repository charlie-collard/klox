package com.github.bspammer.klox

import com.github.bspammer.klox.ast.RuntimeError
import com.github.bspammer.klox.ast.interpret
import com.github.bspammer.klox.parser.Parser
import com.github.bspammer.klox.scanner.Scanner
import com.github.bspammer.klox.scanner.Token
import com.github.bspammer.klox.scanner.TokenType.EOF
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8


object Lox {
    var hadError: Boolean = false
    var hadRuntimeError: Boolean = false

    fun main(vararg args: String) {
        if (args.size > 1) {
            println("Usage: jlox [script]");
            exitProcess(64);
        } else if (args.size == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    fun runFile(path: String) {
        run(File(path).readText(UTF_8))
        if (hadError) exitProcess(65)
        if (hadRuntimeError) exitProcess(70)
    }

    fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            val line = reader.readLine() ?: break
            run(line)
            hadError = false
            hadRuntimeError = false
        }
    }

    fun run(source: String) {
        val tokens = Scanner(source).scanTokens()
        val expr = Parser(tokens).parse()

        if (hadError) return

        expr?.let { println(interpret(it)) }
    }

    fun error(line: Int, message: String, where: String = "") {
        System.err.println("[line $line] Error$where: $message")
        hadError = true
    }

    fun error(token: Token, message: String) {
        if (token.tokenType == EOF) {
            error(token.line, " at end", message)
        } else {
            error(token.line, "at '" + token.lexeme + "'", message)
        }
    }

    fun runtimeError(error: RuntimeError) {
        println("""
            ${error.message}
            [line ${error.token.line}]
        """.trimIndent())
        hadRuntimeError = true
    }

}

fun main(vararg args: String) {
    Lox.main(*args)
}
