package com.example.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    public void start(int port) {
        logger.info("Netty 服务器即将启动，监听端口: {}", port);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            logger.info("Netty 服务器配置完成，开始绑定端口...");
            ChannelFuture future = bootstrap.bind(port).sync();
            channel = future.channel();
            logger.info("Netty 服务器启动成功，监听地址: {}", channel.localAddress());

            future.channel().closeFuture().sync();
            logger.info("Netty 服务器关闭");
        } catch (InterruptedException e) {
            logger.error("Netty 服务器启动失败: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            shutdown();
        }
    }

    @PreDestroy
    public void shutdown() {
        logger.info("Netty 服务器正在关闭...");
        if (channel != null) {
            channel.close();
            logger.info("Netty 服务器通道已关闭");
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        logger.info("Netty 服务器已完全关闭");
    }
}

