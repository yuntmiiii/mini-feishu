package com.example.demo.ws;

/**
 * @ClassName: MyWebSocketServer
 * @Description: TODO
 * @Version 1.0
 * @Date: 2025-01-19 10:47
 * @Auther: UserXin
 */

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * WebSocket服务端
 * @author DavidLei
 *
 */
@Component
@ServerEndpoint("/ws/{clientId}")
public class CustomizedWebSocketServer {

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(CustomizedWebSocketServer.class);

	/**
	 * 在线数
	 */
	private static int onlineCount = 0;

	/**
	 * 线程安全的存储连接session的Map
	 */
	private static Map<String, CustomizedWebSocketServer> clients = new ConcurrentHashMap<String, CustomizedWebSocketServer>();

	/**
	 * session
	 */
	private Session session;

	/**
	 * 客户端端标识
	 */
	private String clientId;

	/**
	 * 客户端连接时方法
	 * @param clientId
	 * @param session
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(@PathParam("clientId") String clientId, Session session) throws IOException {
		logger.info("onOpen: has new client connect -"+clientId);
		this.clientId = clientId;
		this.session = session;
		addOnlineCount();
		clients.put(clientId, this);
		logger.info("onOpen: now has "+onlineCount+" client online");
		
		// 发送欢迎消息
		session.getBasicRemote().sendText("Welcome to WebSocket server! Your clientId: " + clientId);
	}

	/**
	 * 客户端断开连接时方法
	 * @throws IOException
	 */
	@OnClose
	public void onClose() throws IOException {
		logger.info("onClose: has new client close connection -"+clientId);
		clients.remove(clientId);
		subOnlineCount();
		logger.info("onClose: now has "+onlineCount+" client online");
	}

	/**
	 * 收到消息时
	 * @param message
	 * @throws IOException
	 */
    @OnMessage(maxMessageSize=5*1024*1024)
	public void onBinaryMessage(byte[] message) throws IOException {
		logger.info("Received binary: {} bytes", message.length);
		// 原样返回二进制消息
		session.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
	}

	// 同时支持文本和二进制消息
	/**
	 * 收到消息时
	 * @param message
	 * @throws IOException
	 */
	@OnMessage
	public void onTextMessage(String message) throws IOException {
		logger.info("Received text: {}", message);
		// 返回处理后的消息
		String response = "Server received: " + message;
		logger.info("Sending response: {}", response);
		session.getBasicRemote().sendText(response);
	}


	/**
	 * 发生error时
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		logger.error("onError: [clientId: " + clientId + " ,error:" + error.getMessage() + "]", error);
	}

	/**
	 * 指定端末发送消息
	 * @param message
	 * @param clientId
	 * @throws IOException
	 */
	public void sendMessageByClientId(String message, String clientId) throws IOException {
		CustomizedWebSocketServer client = clients.get(clientId);
		if (client != null && client.session.isOpen()) {
			client.session.getBasicRemote().sendText(message);
		}
	}

	/**
	 * 所有端末发送消息
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageAll(String message) throws IOException {
		for (CustomizedWebSocketServer item : clients.values()) {
			if (item.session.isOpen()) {
				item.session.getBasicRemote().sendText(message);
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		CustomizedWebSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		CustomizedWebSocketServer.onlineCount--;
	}

	public static synchronized Map<String, CustomizedWebSocketServer> getClients() {
		return clients;
	}
}

/**
 *
 * WebSocket服务端
 * @author DavidLei
 *
 */
//@Component
//@ServerEndpoint("/webSocket/{clientId}")
//public class CustomizedWebSocketServer {
//
//	/**
//	 * 日志
//	 */
//	private Logger logger = LoggerFactory.getLogger(CustomizedWebSocketServer.class);
//
//	private static final Map<String, CustomizedWebSocketServer> clients = new ConcurrentHashMap<>();
//	private Session session;
//	private String clientId;
//
//	@OnOpen
//	public void onOpen(@PathParam("clientId") String clientId, Session session) throws IOException {
//		if (clients.containsKey(clientId)) {
//			session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "ID冲突"));
//			return;
//		}
//		this.clientId = clientId;
//		this.session = session;
//		clients.put(clientId, this);
//	}
//
//	@OnMessage(maxMessageSize = 1024 * 1024)
//	public void onBinaryMessage(byte[] message) {
//		try {
//			session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message));
//		} catch (Exception e) {
//			logger.error("消息发送失败", e);
//		}
//	}
//
//	public static void sendToClient(String clientId, String message) {
//		CustomizedWebSocketServer client = clients.get(clientId);
//		if (client != null && client.session.isOpen()) {
//			client.session.getAsyncRemote().sendText(message);
//		}
//	}
//
//	public static void broadcast(String message) {
//		clients.values().stream()
//				.filter(client -> client.session.isOpen())
//				.forEach(client -> client.session.getAsyncRemote().sendText(message));
//	}
//}