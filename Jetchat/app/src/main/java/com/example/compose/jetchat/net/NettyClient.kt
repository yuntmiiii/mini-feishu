package com.example.compose.jetchat.net

import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import java.nio.charset.StandardCharsets

object NettyClient {
    private val group = NioEventLoopGroup()
    private var channel: Channel? = null

    fun connect(host: String, port: Int) {
        val bootstrap = Bootstrap()
        bootstrap.group(group)
            .channel(NioSocketChannel::class.java)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    val pipeline = ch.pipeline()
                    pipeline.addLast(StringDecoder(StandardCharsets.UTF_8))
                    pipeline.addLast(StringEncoder(StandardCharsets.UTF_8))
                    pipeline.addLast(SimpleClientHandler()) // 自定义消息处理逻辑
                }
            })

        val future = bootstrap.connect(host, port).sync()
        channel = future.channel()
    }

    fun sendMessage(msg: String) {
        channel?.writeAndFlush(msg + "\n")
    }

    fun disconnect() {
        channel?.close()
        group.shutdownGracefully()
    }
}
