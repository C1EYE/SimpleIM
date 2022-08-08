package com.c1eye.imserver.server;

import com.c1eye.im.codec.SimpleProtobufCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @author c1eye
 * time 2022/8/7 16:20
 */
@Service
@Data
@Slf4j
public class ChatServer {
    @Value("${server.port}")
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ServerBootstrap bootstrap = new ServerBootstrap();

    public void run() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .localAddress(new InetSocketAddress(port))
                     .option(ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT)
                     .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) {
                             ChannelPipeline pipeline = ch.pipeline();
                             pipeline.addLast(new SimpleProtobufCodec());
                         }
                     });
            ChannelFuture channelFuture = bootstrap.bind().sync();
            log.info("聊天服务启动, 端口 " + channelFuture.channel().localAddress());
            // 关闭
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }
}
