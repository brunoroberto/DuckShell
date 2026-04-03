package com.github.brunoroberto.duckshell.core.parser;

import com.github.brunoroberto.duckshell.core.cmd.Command;
import com.github.brunoroberto.duckshell.core.parser.tokens.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DuckParser implements ShellParser {

    private List<Token> tokens;
    private int position;

    @Override
    public CommandNode parse(String input) {
        this.tokens = new Lexer(input).tokenize();
        this.position = 0;
        return parseCommand();
    }

    private Token getCurrentToken() {
        return tokens.get(position);
    }

    private void advance() {
        this.position++;
    }

    private Token getCurrentTokenAndAdvance() {
        return this.tokens.get(this.position++);
    }

    private boolean check(TokenType type) {
        return getCurrentToken().type() == type;
    }

    private Token consume(TokenType type, String errorMessage) {
        return consumeAny(List.of(type), errorMessage);
    }

    private Token consumeAny(List<TokenType> types, String errorMessage) {
        for (TokenType type : types) {
            if (check(type)) {
                return getCurrentTokenAndAdvance();
            }
        }
        throw createError(String.format("%s, but found '%s'", errorMessage, getCurrentToken().type()));
    }

    private IllegalArgumentException createError(String errorMessage) {
        return new IllegalArgumentException(errorMessage);
    }

    private CommandNode parseCommand() {
        if (check(TokenType.EOF)) {
            return new CommandNode("", Collections.emptyList(), Collections.emptyList());
        }
        Token commandToken = consume(TokenType.WORD, "Expected command name");
        String commandName = commandToken.value();
        List<String> arguments = new ArrayList<>();
        List<RedirectionNode> redirections = new ArrayList<>();

        while (!check(TokenType.EOF)) {
            if (check(TokenType.WORD) || check(TokenType.STRING)) {
                arguments.add(getCurrentTokenAndAdvance().value());
            } else if (check(TokenType.REDIRECT_OUT)) {
                advance();
                var targetToken = consumeAny(List.of(TokenType.WORD, TokenType.STRING), "Expected target file");
                redirections.add(new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, targetToken.value()));
            } else if (check(TokenType.REDIRECT_APPEND)) {
                advance();
                var targetToken = consumeAny(List.of(TokenType.WORD, TokenType.STRING), "Expected target file");
                redirections.add(new RedirectionNode(RedirectionType.STDOUT_APPEND, targetToken.value()));
            } else if (check(TokenType.STDERR_OUT)) {
                var targetToken = consumeAny(List.of(TokenType.WORD, TokenType.STRING), "Expected target file");
                redirections.add(new RedirectionNode(RedirectionType.STDERR, targetToken.value()));
            } else {
                throw createError(String.format("Unexpected token '%s'", commandToken.value()));
            }
        }

        return new CommandNode(commandName, arguments, redirections);
    }
}
