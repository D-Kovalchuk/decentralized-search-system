package com.fly.house.io.operations;

import com.fly.house.io.api.Command;

/**
 * Created by dimon on 1/27/14.
 */
public class NoCommand implements Command {

    @Override
    public void execute() {
        System.out.println("no command");
    }
}
