package ru.otus.servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.sockets.ListOfUsersSocket;

public class ListOfUsersSocketServlet extends WebSocketServlet {
	@Override
	public void configure(WebSocketServletFactory webSocketServletFactory) {
		webSocketServletFactory.register(ListOfUsersSocket.class);
	}
}
