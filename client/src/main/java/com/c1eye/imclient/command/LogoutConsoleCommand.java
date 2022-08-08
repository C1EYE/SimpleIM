package com.c1eye.imclient.command;

import com.c1eye.util.Logger;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @author c1eye
 * time 2022/5/12 13:59
 */
@Service
public class LogoutConsoleCommand implements BaseConsoleCommand{

    public static final String KEY = "3";
    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "退出";
    }

    @Override
    public void execute(Scanner sc) {
        Logger.cfo("退出命令执行成功");
    }
}
