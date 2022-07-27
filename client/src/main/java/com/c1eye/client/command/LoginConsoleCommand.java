package com.c1eye.client.command;

import lombok.Getter;

import java.util.Scanner;

/**
 * @author c1eye
 * time 2022/5/8 20:46
 */
@Getter
public class LoginConsoleCommand implements BaseConsoleCommand {

    private String username;
    private String password;
    public static final String KEY = "1";
    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "登录";
    }

    @Override
    public void execute(Scanner sc) {
        System.out.println("请输入用户信息：");
        while (true) {
            String line = sc.nextLine();
            String[] info = line.split("@");
            if(info.length != 2){
                System.out.println("请输入正确的信息");
            }else {
                System.out.println("登陆成功");
                username = info[0];
                password = info[1];
                System.out.println("当前用户为："+username);
                break;
            }
        }
    }
}
