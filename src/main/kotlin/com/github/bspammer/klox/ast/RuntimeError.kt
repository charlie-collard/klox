package com.github.bspammer.klox.ast

import com.github.bspammer.klox.scanner.Token

class RuntimeError(val token: Token, override val message: String): RuntimeException()
