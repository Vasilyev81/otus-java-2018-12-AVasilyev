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
        DBPreparation DBPreparation = new DBPreparation(context, dbAddress);
        DBService dbService = DBPreparation.getDbService();
        dbService.init();

        Address frontAddress = new Address("Frontend");
        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        context.init();

        new ServerConfiguration(frontendService).start();



    }
}