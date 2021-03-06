package ru.otus.messageSystem.messages.toDB;

import ru.otus.dao.DBService;
import ru.otus.messageSystem.*;
import ru.otus.messageSystem.messages.Message;

public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
