package com.github.bspammer.klox.ast

import com.github.bspammer.klox.Lox
import com.github.bspammer.klox.ast.Expr.*
import com.github.bspammer.klox.scanner.Token
import com.github.bspammer.klox.scanner.TokenType.*

fun interpret(expr: Expr): Any? {
    return try {
        doInterpret(expr)
    } catch (error: RuntimeError) {
        Lox.runtimeError(error)
        null
    }
}

private fun doInterpret(expr: Expr): Any? {
    return when (expr) {
        is Literal -> expr.value
        is Grouping -> interpret(expr.expression)
        is Unary -> {
            val right = interpret(expr.right)
            when (expr.operator.tokenType) {
                MINUS -> -assertNumber(expr.operator, right)
                BANG -> !isTruthy(right)
                else -> throw RuntimeException("Unknown unary operator type ${expr.operator.tokenType}")
            }
        }

        is Binary -> {
            val left = interpret(expr.left)
            val right = interpret(expr.right)
            val op = expr.operator

            when (op.tokenType) {
                MINUS -> assertNumber(op, left) - assertNumber(op, right)
                SLASH -> assertNumber(op, left) / assertNumber(op, right)
                STAR -> assertNumber(op, left) * assertNumber(op, right)
                PLUS -> {
                    if (left is Double && right is Double) {
                        return left + right
                    }
                    if (left is String && right is String) {
                        return left + right
                    }
                    throw RuntimeError(op, "Operands must be two numbers or two strings.")
                }

                GREATER -> assertNumber(op, left) > assertNumber(op, right)
                GREATER_EQUAL -> assertNumber(op, left) >= assertNumber(op, right)
                LESS -> assertNumber(op, left) < assertNumber(op, right)
                LESS_EQUAL -> assertNumber(op, left) <= assertNumber(op, right)
                BANG_EQUAL -> left != right
                EQUAL_EQUAL -> left == right
                else -> throw RuntimeException("Unknown binary operator type ${op.tokenType}")
            }
        }
    }
}

private fun isTruthy(any: Any?): Boolean {
    if (any == null) return false
    if (any is Boolean) return any
    return true
}

private fun assertNumber(token: Token, operand: Any?): Double {
    if (operand is Double) return operand
    throw RuntimeError(token, "Operand must be a number.")
}
