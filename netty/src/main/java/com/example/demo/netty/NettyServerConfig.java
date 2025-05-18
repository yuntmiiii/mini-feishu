package com.example.demo.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyServerConfig {

    @Bean
    public CommandLineRunner startNettyServer(NettyServer nettyServer) {
        return args -> new Thread(() -> nettyServer.start(8081)).start();
    }
}

