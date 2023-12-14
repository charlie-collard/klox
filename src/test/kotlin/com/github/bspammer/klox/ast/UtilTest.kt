package com.github.bspammer.klox.ast

import com.github.bspammer.klox.scanner.Token
import com.github.bspammer.klox.scanner.TokenType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UtilTest {
    @Test
    fun `simple addition`() {
        assertThat(
            exprToString(
                Expr.Binary(
                    Expr.Unary(Token(TokenType.MINUS, "-", null, 1), Expr.Literal(123)),
                    Token(TokenType.STAR, "*", null, 1),
                    Expr.Grouping(Expr.Literal(45.67))
                )
            )
        ).isEqualTo("(* (- 123) (45.67))")
    }
}
