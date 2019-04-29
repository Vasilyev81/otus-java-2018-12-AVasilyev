package ru.otus;

import ru.otus.dao.DBPreparation;
import ru.otus.serverconfig.ServerConfiguration;

public class Main {
    public static void main(String[] args) throws Exception {
        DBPreparation DBPreparation = new DBPreparation();
        new ServerConfiguration(DBPreparation.getDbService()).start();
    }
}