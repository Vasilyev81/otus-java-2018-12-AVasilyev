package ru.otus.sockets;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.backend.chatService.dto.ChatMessage;
import ru.otus.frontend.FrontendService;

import java.io.IOException;
import java.util.logging.Logger;


@WebSocket
public class ChatSocket {
	private Session session;
	@Autowired
	private FrontendService frontendService;
	private final static Logger log = Logger.getLogger(ChatSocket.class.getName());


	@OnWebSocketConnect
	public void onConnect(Session session) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.session = session;
		frontendService.register(this);
		System.out.println("FrontendService: nonNull ");
	}

	@OnWebSocketMessage
	public void onText(String message) {
		if (!message.isEmpty()) frontendService.addChatMessage(new ChatMessage(message));
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
		frontendService.unregister(this);
	}

	public void sendMessage(String message) {
		try {
			this.session.getRemote().sendString(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

