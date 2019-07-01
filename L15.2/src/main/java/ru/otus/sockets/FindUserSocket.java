package ru.otus.sockets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.datasets.UserDataSet;
import ru.otus.frontend.FrontendService;

import java.io.IOException;

@WebSocket
public class FindUserSocket {
	private Session session;
	private String sessionId;
	@Autowired
	private FrontendService frontendService;
	private final static Gson gson = new Gson();

	@OnWebSocketConnect
	public void onConnect(Session session) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.session = session;
	}

	@OnWebSocketMessage
	public void onMessage(String message) {
		JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
		long userId = jsonObject.get("userId").getAsLong();
		sessionId = jsonObject.get("sessionId").getAsString();
		frontendService.register(sessionId, this);
		frontendService.findUserByID(userId, sessionId);
	}

	public void sendMessage(UserDataSet user) {
		String response;
		if (user != null) {
			response = gson.toJson(user);
			System.out.println(response);
		} else {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", false);
			response = jsonObject.toString();
		}
		try {
			session.getRemote().sendString(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
