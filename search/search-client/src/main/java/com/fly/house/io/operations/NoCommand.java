package com.fly.house.io.operations;

/**
 * Created by dimon on 1/27/14.
 */
public class NoCommand implements Command {

    @Override
    public void execute() {
        System.out.println("no command");
    }
}
