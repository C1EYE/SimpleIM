package com.c1eye.client.command;

import lombok.Data;
import lombok.Setter;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author c1eye
 * time 2022/5/11 16:16
 */
@Data
public class MenuConsoleCommand implements BaseConsoleCommand{
    public static final String KEY = "0";
    String allCommand;
    String input;
    
    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "show 所有指令";
    }

    @Override
    public void execute(Scanner sc) {
        System.out.println("请输入指令：" + allCommand);
        input = inputCommand(sc);
    }

    private String inputCommand(Scanner sc) {
        return sc.next();
    }


    public void setAllCommandByMap(Map<String, BaseConsoleCommand> commandMap) {
        Set<Map.Entry<String, BaseConsoleCommand>> entrys =
                commandMap.entrySet();
        Iterator<Map.Entry<String, BaseConsoleCommand>> iterator =
                entrys.iterator();

        StringBuilder menus = new StringBuilder();
        menus.append("[menu] ");
        while (iterator.hasNext()) {
            BaseConsoleCommand next = iterator.next().getValue();

            menus.append(next.getKey())
                 .append("->")
                 .append(next.getTip())
                 .append(" | ");
        }
        allCommand = menus.toString();
    }
    
    


}
