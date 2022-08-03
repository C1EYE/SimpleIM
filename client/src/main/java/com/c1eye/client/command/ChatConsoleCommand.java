package com.c1eye.client.command;

import java.util.Scanner;

/**
 * @author c1eye
 * time 2022/5/9 16:28
 */
public class ChatConsoleCommand implements BaseConsoleCommand {
    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getTip() {
        return null;
    }

    @Override
    public void execute(Scanner sc) {
        System.out.println("");
    }
}
