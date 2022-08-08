package com.c1eye.imclient.command;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @author c1eye
 * time 2022/5/9 16:28
 */
@Service
@Data
public class ChatConsoleCommand implements BaseConsoleCommand {
    private String toUserId;
    private String message;
    public static final String KEY = "2";

    @Override
    public void execute(Scanner scanner) {
        System.out.print("请输入聊天的消息(id:message)：");
        String[] info = null;
        while (true) {
            String input = scanner.next();
            info = input.split(":");
            if (info.length != 2) {
                System.out.println("请输入聊天的消息(id:message):");
            }else {
                break;
            }
        }
        toUserId = info[0];
        message =  info[1];
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return null;
    }

}
