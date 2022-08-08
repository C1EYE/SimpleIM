package com.c1eye;


import com.c1eye.imserver.server.ChatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class ServerApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 启动并初始化 Spring 环境及其各 Spring 组件
        ApplicationContext context =
                SpringApplication.run(ServerApplication.class, args);


//        if (args.length > 0 &&
//                (args[0] != null && args[0].equalsIgnoreCase("soultest1"))) {
//            //启动 灵魂测试1  服务器
//            startSoultest1Server(context, args);
//
//        } else if (args.length > 0 &&
//                (args[0] != null && args[0].equalsIgnoreCase("multiEcho"))) {
//            //启动重复回显的服务器
//            startMultiEchoServer(context, args);
//        } else {
            //启动聊天服务器
            startChatServer(context);
//        }
    }

//    //启动 重复回显的服务器
//    private static void startMultiEchoServer(ApplicationContext context, String[] args) {
//
//        MultiEchoServer multiEchoServer = context.getBean(MultiEchoServer.class);
//        MultiEchoHandler multiEchoHandler = context.getBean(MultiEchoHandler.class);
//        if (args.length > 1 && args[1] != null) {
//            //重复回显的次数
//            multiEchoHandler.repeat_time = Integer.parseInt(args[1]);
//        }
//        multiEchoServer.run();
//
//    }
//
//    //启动 灵魂测试 服务端 1
//    private static void startSoultest1Server(ApplicationContext context, String[] args) {
//
//        SoulTest1Server soulTest1Server = context.getBean(SoulTest1Server.class);
//        SoulTest1ServerHandler soulTest1ServerHandler = context.getBean(SoulTest1ServerHandler.class);
//
//        if (args.length > 1 && args[1] != null) {
//            //设置的端口数，如100
//            SoulTest1Server.serverPortCount = Long.parseLong(args[1]);
//            log.debug("设置的端口数，{}", SoulTest1Server.serverPortCount);
//
//            soulTest1ServerHandler.init();
//        }
//        soulTest1Server.run();
//
//    }

    /**
     * 聊天服务启动
     * @param context
     */
    private static void startChatServer(ApplicationContext context) {
        ChatServer nettyServer =  context.getBean(ChatServer.class);
        nettyServer.run();
    }


}

