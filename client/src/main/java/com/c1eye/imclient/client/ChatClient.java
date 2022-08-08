package com.c1eye.imclient.client;

import com.c1eye.im.codec.SimpleProtobufCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author c1eye
 * time 2022/8/7 20:48
 */
@Slf4j
@Service
@Data
public class ChatClient {
    /**
     * 服务器ip地址
     */
    @Value("${chat.server.ip}")
    private String host;
    /**
     * 服务器端口
     */
    @Value("${chat.server.port}")
    private int port;

    private final NioEventLoopGroup group;

    private GenericFutureListener<ChannelFuture> connectedListener = channelFuture -> {
        System.out.println("默认实现");
    };

    public ChatClient() {
        group = new NioEventLoopGroup(1);
    }

    public void doConnect() {
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.remoteAddress(host, port);

            // 设置通道初始化
            bootstrap.handler(
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline()
                              .addLast("codec", new SimpleProtobufCodec());
                        }
                    }
                             );

            // 异步发起连接
            ChannelFuture future = bootstrap.connect();
            future.addListener(connectedListener);


            // 阻塞关闭
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            log.info("客户端连接失败!" + e.getMessage());
        }finally {
            close();
        }
    }

    public void close() {
        group.shutdownGracefully();
    }

}
