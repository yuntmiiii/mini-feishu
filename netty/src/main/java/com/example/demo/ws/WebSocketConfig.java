package com.example.demo.ws;

/**
 * @ClassName: WebSocketConfig
 * @Description: TODO
 * @Version 1.0
 * @Date: 2025-01-19 10:48
 * @Auther: UserXin
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket 服务端配置类
 */
@Configuration
public class WebSocketConfig {

	/**
	 * ServerEndpointExporter bean 注入
	 * @return
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
		return serverEndpointExporter;
	}

}

