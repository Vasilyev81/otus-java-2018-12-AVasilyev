package ru.otus.servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.sockets.ChatSocket;

public class ChatSocketServlet extends WebSocketServlet {
	public void configure(WebSocketServletFactory factory) {
		factory.getPolicy().setIdleTimeout(600000);
		factory.register(ChatSocket.class);
	}
}
