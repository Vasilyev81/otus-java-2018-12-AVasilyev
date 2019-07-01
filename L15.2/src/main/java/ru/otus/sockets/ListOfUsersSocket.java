package ru.otus.sockets;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.datasets.UserDataSet;
import ru.otus.frontend.FrontendService;

import java.io.IOException;
import java.util.List;

@WebSocket
public class ListOfUsersSocket {
	private Session session;
	private String sessionId;
	private Gson gson = new Gson();
	@Autowired
	private FrontendService frontendService;


	@OnWebSocketConnect
	public void onConnect(Session session) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.session = session;
	}

	public void sendAnswer(List<UserDataSet> users){
		String json = gson.toJson(users);
		try {
			session.getRemote().sendString(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnWebSocketMessage
	public void onText(String id) {
		sessionId = id;
		frontendService.register(sessionId, this);
		frontendService.readAll(sessionId);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		frontendService.unregister(sessionId);
	}

	public Session getSession() {
		return session;
	}
}
