package com.github.brunoroberto.duckshell.core.cmd.ext;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandExecutor;
import com.github.brunoroberto.duckshell.core.cmd.Result;

public class ExternalCommandExecutor implements CommandExecutor<ExternalCommand> {

    @Override
    public Result execute(Context context, ExternalCommand command) {
        return command.execute(context);
    }
}
