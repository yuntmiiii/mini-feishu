package com.example.demo.netty;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserChannelManager {
    private static final Logger logger = LoggerFactory.getLogger(UserChannelManager.class);
    private static final Map<String, Channel> userChannels = new ConcurrentHashMap<>();

    public static void addUser(String userId, Channel channel) {
        userChannels.put(userId, channel);
        logger.info("用户 {} 已上线，当前在线用户数: {}", userId, userChannels.size());
    }

    public static void removeUser(String userId) {
        userChannels.remove(userId);
        logger.info("用户 {} 已下线，当前在线用户数: {}", userId, userChannels.size());
    }

    public static Channel getChannel(String userId) {
        return userChannels.get(userId);
    }

    public static void sendMessage(String fromUserId, String toUserId, String message) {
        Channel targetChannel = getChannel(toUserId);
        if (targetChannel != null && targetChannel.isActive()) {
            String formattedMessage = String.format("[%s] 对你说: %s", fromUserId, message);
            targetChannel.writeAndFlush(formattedMessage);
            logger.info("消息已从 {} 发送到 {}: {}", fromUserId, toUserId, message);
        } else {
            logger.warn("用户 {} 不在线，消息发送失败", toUserId);
        }
    }
} 