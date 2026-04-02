package com.github.brunoroberto.duckshell.core.parser.tokens;

public enum TokenType {
    WORD,
    STRING,
    PIPE,           // |
    REDIRECT_OUT,   // > or 1>
    REDIRECT_APPEND,// >>
    REDIRECT_IN,    // <
    STDERR_OUT,     // 2>
    AND,            // &&
    OR,             // ||
    SEMICOLON,      // ;
    EOF
}
