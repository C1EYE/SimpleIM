package com.c1eye.client;

import com.c1eye.client.command.LoginConsoleCommand;
import com.c1eye.client.command.MenuConsoleCommand;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * @author c1eye
 * time 2022/5/11 15:47
 */

public class TestClientCommand {
    @Test
    void testLogin() {
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        loginConsoleCommand.execute(new Scanner("admin@admin"));
    }

    @Test
    void testMenu() {
        MenuConsoleCommand commandMenu = new MenuConsoleCommand();
        commandMenu.setAllCommand("[menu] 0->show 所有命令 | 1->登录 | ...");
        Scanner scanner = new Scanner(System.in);
        //处理命令
        while (true) {
            commandMenu.execute(scanner);
        }
    }
}
