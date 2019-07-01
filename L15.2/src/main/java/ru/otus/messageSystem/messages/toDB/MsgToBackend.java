package ru.otus.messageSystem.messages.toDB;

import ru.otus.backend.BackendService;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.messages.Message;

public abstract class MsgToBackend extends Message {
    public MsgToBackend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof BackendService) {
            exec((BackendService) addressee);
        }
    }

    public abstract void exec(BackendService backendService);
}
