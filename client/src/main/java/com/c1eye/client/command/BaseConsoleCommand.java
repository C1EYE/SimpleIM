package com.c1eye.client.command;

import java.util.Scanner;

/**
 * @author c1eye
 * time 2022/5/8 19:50
 */
public interface BaseConsoleCommand {
    /**
     * 获取key
     * @return
     */
    String getKey();

    /**
     * 获取提示消息
     * @return
     */
    String getTip();
    /**
     * 执行
     * @param sc
     */
    void execute(Scanner sc);


}
