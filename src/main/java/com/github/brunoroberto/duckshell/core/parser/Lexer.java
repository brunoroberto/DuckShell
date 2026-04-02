package com.github.brunoroberto.duckshell.core.parser;

import com.github.brunoroberto.duckshell.core.parser.tokens.Token;
import com.github.brunoroberto.duckshell.core.parser.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String input;
    private int position;

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (!isAtEnd()) {
            skipWhitespace();

            if (isAtEnd()) {
                break;
            }

            char c = getCurrentChar();

            if (isRedirectOperator(c)) {
                tokens.add(readOperator());
            } else if (c == '"' || c == '\'') {
                tokens.add(readQuotedString());
            } else {
                tokens.add(readWord());
            }
        }

        tokens.add(new Token(TokenType.EOF, "", position));
        return tokens;
    }

    private Token readWord() {
        int start = position;
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd()) {
            char c = getCurrentChar();

            if (Character.isWhitespace(c) || isRedirectOperator(c)) {
                break;
            }

            if (c == '\\') {
                advance();
                if (!isAtEnd()) {
                    sb.append(getCurrentChar());
                    advance();
                }
            } else {
                sb.append(c);
                advance();
            }
        }

        return new Token(TokenType.WORD, sb.toString(), start);
    }

    private Token readQuotedString() {
        int start = position;
        char quote = getCurrentChar();
        advance();

        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && getCurrentChar() != quote) {
            if (getCurrentChar() == '\\' && quote == '"') {
                advance();
                if (!isAtEnd()) {
                    sb.append(getCurrentChar());
                    advance();
                }
            } else {
                sb.append(getCurrentChar());
                advance();
            }
        }

        if (isAtEnd()) {
            throw new IllegalArgumentException(String.format("Unterminated quoted string %s at position: %d", sb.toString(), position));
        }

        advance();
        return new Token(TokenType.STRING, sb.toString(), start);
    }

    private Token readOperator() {
        int start = position;
        char currentChar = getCurrentChar();
        if (currentChar == '1' && lookAhead() == '>') {
            advance();
            advance();
            return new Token(TokenType.REDIRECT_OUT, "1>", start);
        }
        if (currentChar == '2' && lookAhead() == '>') {
            advance();
            advance();
            return new Token(TokenType.STDERR_OUT, "2>", start);
        }
        if (currentChar == '>') {
            if (lookAhead() == '>') {
                advance();
                advance();
                return new Token(TokenType.REDIRECT_APPEND, ">>", start);
            }
            advance();
            return new Token(TokenType.REDIRECT_OUT, ">", start);
        }
        throw new IllegalArgumentException(String.format("Unrecognized operator '%c' at position: %d ", currentChar, position));
    }

    private boolean isRedirectOperator(char currentChar) {
        return currentChar == '>' || (currentChar == '1' || currentChar == '2' && lookAhead() == '>');
    }

    private boolean isAtEnd() {
        return this.position >= this.input.length();
    }

    private char getCurrentChar() {
        return this.input.charAt(this.position);
    }

    private char lookAhead() {
        return peekChar(1);
    }

    private char peekChar(int offset) {
        int index = position + offset;
        if (index >= input.length()) {
            return '\0';
        }
        return input.charAt(index);
    }

    private void advance() {
        this.position++;
    }

    private void skipWhitespace() {
        while (!isAtEnd() && Character.isWhitespace(getCurrentChar())) {
            advance();
        }
    }

}
