package ru.otus.backend.chatService;

import ru.otus.backend.chatService.dto.ChatMessage;

public interface ChatService {
	void addNewMessage(ChatMessage message);


	String getAllMessages();
}
