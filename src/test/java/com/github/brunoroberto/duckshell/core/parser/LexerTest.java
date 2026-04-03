package com.github.brunoroberto.duckshell.core.parser;

import com.github.brunoroberto.duckshell.core.parser.tokens.Token;
import com.github.brunoroberto.duckshell.core.parser.tokens.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void tokenizeSimpleWord() {
        var tokens = new Lexer("hello").tokenize();
        assertEquals(2, tokens.size());
        assertEquals(TokenType.WORD, tokens.get(0).type());
        assertEquals("hello", tokens.get(0).value());
        assertEquals(TokenType.EOF, tokens.get(1).type());
    }

    @Test
    void tokenizeMultipleWords() {
        var tokens = new Lexer("echo hello world").tokenize();
        assertEquals(4, tokens.size());
        assertEquals("echo", tokens.get(0).value());
        assertEquals("hello", tokens.get(1).value());
        assertEquals("world", tokens.get(2).value());
        assertEquals(TokenType.EOF, tokens.get(3).type());
    }

    @Test
    void tokenizeDoubleQuotedString() {
        var tokens = new Lexer("echo \"hello world\"").tokenize();
        assertEquals(3, tokens.size());
        assertEquals(TokenType.WORD, tokens.get(0).type());
        assertEquals(TokenType.STRING, tokens.get(1).type());
        assertEquals("hello world", tokens.get(1).value());
    }

    @Test
    void tokenizeSingleQuotedString() {
        var tokens = new Lexer("echo 'hello world'").tokenize();
        assertEquals(3, tokens.size());
        assertEquals(TokenType.STRING, tokens.get(1).type());
        assertEquals("hello world", tokens.get(1).value());
    }

    @Test
    void doubleQuotedStringSupportsEscapes() {
        var tokens = new Lexer("echo \"hello\\\"world\"").tokenize();
        assertEquals(3, tokens.size());
        assertEquals("hello\"world", tokens.get(1).value());
    }

    @Test
    void singleQuotedStringDoesNotProcessEscapes() {
        var tokens = new Lexer("echo 'hello\\nworld'").tokenize();
        assertEquals(3, tokens.size());
        assertEquals("hello\\nworld", tokens.get(1).value());
    }

    @Test
    void tokenizeRedirectOut() {
        var tokens = new Lexer("echo hello > file.txt").tokenize();
        assertEquals(5, tokens.size());
        assertEquals(TokenType.REDIRECT_OUT, tokens.get(2).type());
        assertEquals(">", tokens.get(2).value());
    }

    @Test
    void tokenizeExplicitStdoutRedirect() {
        var tokens = new Lexer("echo hello 1> file.txt").tokenize();
        var redirectToken = tokens.stream()
                .filter(t -> t.type() == TokenType.REDIRECT_OUT)
                .findFirst();
        assertTrue(redirectToken.isPresent());
        assertEquals("1>", redirectToken.get().value());
    }

    @Test
    void tokenizeRedirectAppend() {
        var tokens = new Lexer("echo hello >> file.txt").tokenize();
        var appendToken = tokens.stream()
                .filter(t -> t.type() == TokenType.REDIRECT_APPEND)
                .findFirst();
        assertTrue(appendToken.isPresent());
        assertEquals(">>", appendToken.get().value());
    }

    @Test
    void tokenizeStderrRedirect() {
        var tokens = new Lexer("echo hello 2> error.log").tokenize();
        var stderrToken = tokens.stream()
                .filter(t -> t.type() == TokenType.STDERR_OUT)
                .findFirst();
        assertTrue(stderrToken.isPresent());
        assertEquals("2>", stderrToken.get().value());
    }

    @Test
    void emptyInputProducesOnlyEof() {
        var tokens = new Lexer("").tokenize();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type());
    }

    @Test
    void whitespaceOnlyInputProducesOnlyEof() {
        var tokens = new Lexer("   \t  ").tokenize();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type());
    }

    @Test
    void backslashEscapeInWord() {
        var tokens = new Lexer("hello\\ world").tokenize();
        assertEquals(2, tokens.size());
        assertEquals("hello world", tokens.get(0).value());
    }

    @Test
    void unterminatedDoubleQuoteThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lexer("echo \"unterminated").tokenize());
    }

    @Test
    void unterminatedSingleQuoteThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lexer("echo 'unterminated").tokenize());
    }

    @Test
    void tokenPositionsAreTracked() {
        var tokens = new Lexer("echo hello").tokenize();
        assertEquals(0, tokens.get(0).position());
        assertEquals(5, tokens.get(1).position());
    }

    @Test
    void multipleSpacesBetweenWords() {
        var tokens = new Lexer("echo    hello     world").tokenize();
        assertEquals(4, tokens.size());
        assertEquals("echo", tokens.get(0).value());
        assertEquals("hello", tokens.get(1).value());
        assertEquals("world", tokens.get(2).value());
    }
}
