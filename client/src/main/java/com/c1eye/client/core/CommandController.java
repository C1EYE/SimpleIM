package com.c1eye.client.core;

import com.c1eye.client.command.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author c1eye
 * time 2022/5/9 16:42
 */

public class CommandController {

    MenuConsoleCommand menuConsoleCommand = new MenuConsoleCommand();
    HashMap<String, BaseConsoleCommand> commandMap = new HashMap<>();

    @Test
    public void testCommandController() {
        initCommandMap();
        //处理命令
        while (true) {
            //菜单输入
            Scanner scanner = new Scanner(System.in);
            menuConsoleCommand.execute(scanner);
            String key = menuConsoleCommand.getInput();

            //根据菜单输入，选择正确的命令收集器
            BaseConsoleCommand command = commandMap.get(key);

            if (null == command) {
                System.err.println("无法识别[" + key + "]指令，请重新输入!");
                continue;
            }

            //执行命令收集器
            switch (key) {
                case LoginConsoleCommand.KEY:
                    command.execute(scanner);
//                    startLogin((LoginConsoleCommand) command);
                    break;

                case LogoutConsoleCommand.KEY:
                    command.execute(scanner);
//                    startLogout((LoginConsoleCommand) command);
                    break;
                default: break;
            }

        }
    }

    public void initCommandMap() {
        commandMap = new HashMap<>();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        LogoutConsoleCommand logoutConsoleCommand = new LogoutConsoleCommand();
        commandMap.put(menuConsoleCommand.getKey(), menuConsoleCommand);
        commandMap.put(loginConsoleCommand.getKey(), loginConsoleCommand);
        commandMap.put(logoutConsoleCommand.getKey(), logoutConsoleCommand);


        menuConsoleCommand.setAllCommandByMap(commandMap);


    }
}
