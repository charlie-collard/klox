package com.github.bspammer.klox.ast

import com.github.bspammer.klox.scanner.Token

sealed interface Expr {
    data class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr
    data class Grouping(val expression: Expr) : Expr
    data class Literal(val value: Any?) : Expr
    data class Unary(val operator: Token, val right: Expr) : Expr
}
