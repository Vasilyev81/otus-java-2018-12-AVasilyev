package ru.otus.backend.chatService;

import ru.otus.backend.chatService.dto.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ChatServiceImpl implements ChatService {
    private final static List<ChatMessage> messages = new ArrayList<>();

    public ChatServiceImpl() {
        messages.add(new ChatMessage("First message"));
        messages.add(new ChatMessage("Second message"));
    }

    @Override
    public void addNewMessage(ChatMessage message) {
        messages.add(message);
    }

    @Override
    public String getAllMessages() {
        StringBuilder builder = new StringBuilder("Hello!!! Write something \n");
        String allMessages = messages.stream().map(ChatMessage::getText).collect(Collectors.joining("\n", "", ""));
        builder.append(allMessages);
        return builder.toString();
    }
}
