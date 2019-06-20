package ru.otus;

import ru.otus.messageSystem.MessageSystemContext;
import ru.otus.dao.*;
import ru.otus.messageSystem.*;
import ru.otus.serverconfig.ServerConfiguration;
import ru.otus.servlets.*;

public class Main {
    public static void main(String[] args) throws Exception {

        MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext context = new MessageSystemContext(messageSystem);

        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);
        DBPreparation DBPreparation = new DBPreparation(context, dbAddress);
        DBService dbService = DBPreparation.getDbService();
        dbService.init();

        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();
        messageSystem.start();
        new ServerConfiguration(frontendService).start();



    }
}