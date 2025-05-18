package com.example.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private String userId;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("新的客户端连接: {}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // 消息格式：login:userId 或 chat:toUserId:message
        if (msg.startsWith("login:")) {
            // 处理登录
            userId = msg.substring(6);
            UserChannelManager.addUser(userId, ctx.channel());
            ctx.writeAndFlush("登录成功，你的ID是: " + userId);
        } else if (msg.startsWith("chat:")) {
            // 处理聊天消息
            if (userId == null) {
                ctx.writeAndFlush("请先登录！");
                return;
            }
            
            String[] parts = msg.substring(5).split(":", 2);
            if (parts.length != 2) {
                ctx.writeAndFlush("消息格式错误，正确格式：chat:toUserId:message");
                return;
            }
            
            String toUserId = parts[0];
            String message = parts[1];
            
            // 转发消息
            UserChannelManager.sendMessage(userId, toUserId, message);
            ctx.writeAndFlush("消息已发送给 " + toUserId);
        } else {
            ctx.writeAndFlush("未知命令，请使用 login:userId 或 chat:toUserId:message");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (userId != null) {
            UserChannelManager.removeUser(userId);
        }
        logger.info("客户端断开连接: {}", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("处理消息时发生错误: {}", cause.getMessage(), cause);
        ctx.close();
    }
}

