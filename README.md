# DuckShell

A mini shell written in Java from scratch — built for fun and learning.

DuckShell is a toy Unix-like shell that implements the core stages of command interpretation: lexing raw input into tokens, parsing tokens into an AST, resolving commands, and executing them. It's not meant to replace Bash — it's meant to demystify what happens between pressing Enter and seeing output.

```
/Users/bruno/projects |quack > echo "hello world"
hello world
```

## Why?

Shells feel like magic until you build one. This project explores:

- How raw text like `echo "hello world" > out.txt` gets broken into meaningful tokens
- How tokens get structured into a command with arguments and redirections
- How a REPL loop ties everything together
- How to design a command architecture that separates built-ins from external commands

## Architecture

DuckShell follows a clean pipeline from input to output:

```
Raw Input
  |
  v
[Lexer] -----> List<Token>        (tokenization: words, strings, operators)
  |
  v
[Parser] ----> CommandNode         (AST: command name, arguments, redirections)
  |
  v
[Resolver] --> Command             (maps name to a concrete command: EchoCmd, CdCmd, ...)
  |
  v
[Executor] --> Result              (runs the command, produces output)
  |
  v
[Output] ----> Console / File      (routes output based on redirections)
```

### Lexer

Converts raw input into a stream of typed tokens. Handles:

- Unquoted words (`hello`)
- Double-quoted strings with escape support (`"hello \"world\""`)
- Single-quoted strings taken literally (`'no\nescapes'`)
- Redirection operators (`>`, `>>`, `1>`, `2>`, `2>>`)

### Parser

Consumes the token stream and produces a `CommandNode` — a record containing the command name, a list of arguments, and a list of redirections. This is the AST of the shell.

### Command Resolution & Execution

`CommandResolver` maps a command name to a `Command` instance. Commands are split into two families:

- **Built-in commands** (`ShellCommand`) — handled directly by the shell (e.g. `echo`, `cd`, `pwd`)
- **External commands** (`ExternalCommand`) — delegated to the OS via `ProcessBuilder`, with concurrent stdout/stderr capture

`CommandExecutorFactory` selects the right executor based on command type, keeping the REPL loop decoupled from execution details.

### Output

`ResultOutput` routes command results based on redirections:

- **Console** — default output via `ConsoleOutput`
- **File** — when redirections are present, `FileOutput` writes to files (supports overwrite and append modes)

## Built-in Commands

| Command | Description |
|---------|-------------|
| `echo`  | Prints arguments to stdout, supports redirections |
| `exit`  | Exits the shell |
| `pwd`   | Prints the current working directory |
| `cd`    | Changes directory (supports absolute, relative, `~`) |
| `type`  | Identifies whether a command is a shell builtin or external |
| `quack` | Easter egg — quacks like a duck |
| `clear` | Clears the terminal screen |

## Project Structure

```
src/main/java/com/github/brunoroberto/
  Main.java                              # Entry point
  duckshell/core/
    DuckShell.java                       # REPL loop
    Shell.java                           # Shell interface
    Context.java                         # Shell state (cwd, output, PATH)
    OSPath.java                          # Executable lookup via $PATH
    ErrorCmdResult.java                  # Error result record
    parser/
      Lexer.java                         # Tokenizer
      DuckParser.java                    # Token stream -> AST
      ShellParser.java                   # Parser interface
      tokens/
        Token.java                       # Token record
        TokenType.java                   # Token type enum
        CommandNode.java                 # Parsed command AST
        RedirectionNode.java             # Redirection record
        RedirectionType.java             # Redirection type enum
    cmd/
      Command.java                       # Command interface
      Result.java                        # Result interface
      SuccessCmdResult.java              # Success result record
      EmptyCmdResult.java                # Empty result record
      CommandNames.java                  # Known command name registry
      CommandResolver.java               # Name -> Command mapping
      CommandExecutor.java               # Executor interface
      CommandExecutorFactory.java        # Executor selection
      builtin/
        ShellCommand.java                # Built-in command marker
        ShellCommandExecutor.java        # Built-in executor
        EchoCmd.java                     # echo implementation
        ExitCmd.java                     # exit implementation
        PwdCmd.java                      # pwd implementation
        CdCmd.java                       # cd implementation
        TypeCmd.java                     # type implementation
        QuackCmd.java                    # quack implementation
        ClearCmd.java                    # clear implementation
        InvalidCmd.java                  # Unknown command handler
      ext/
        ExternalCommand.java             # External command wrapper
        ExternalCommandExecutor.java     # External command executor
    io/
      ShellOutput.java                   # Output interface
      ConsoleOutput.java                 # Console output impl
      ResultOutput.java                  # Output router (console vs file)
      FileOutput.java                    # File output for redirections
```

## Building & Running

Requires **Java 21+** and **Gradle**.

```bash
# Build
./gradlew build

# Run tests
./gradlew test

# Run the shell
./gradlew run
```

## What's Next

This is a work in progress. Possible next steps:

- Pipe operator (`|`) support
- Logical operators (`&&`, `||`)
- Environment variables
- Command history

## License

This is a personal learning project. Feel free to use it as reference or inspiration for your own shell.
