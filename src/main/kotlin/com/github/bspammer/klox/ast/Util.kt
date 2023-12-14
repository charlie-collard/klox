package com.github.bspammer.klox.ast

import com.github.bspammer.klox.ast.Expr.*

fun exprToString(expr: Expr): String {
    return when (expr) {
        is Binary -> "(${expr.operator.lexeme} ${exprToString(expr.left)} ${exprToString(expr.right)})"
        is Grouping -> "(${exprToString(expr.expression)})"
        is Literal -> expr.value.toString()
        is Unary -> "(${expr.operator.lexeme} ${exprToString(expr.right)})"
    }
}
