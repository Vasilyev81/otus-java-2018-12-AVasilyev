package ru.otus;

import ru.otus.serverconfig.ServerConfiguration;

public class Main {
    public static void main(String[] args) throws Exception {
        DaoAccess daoAccess = new DaoAccess();
        new ServerConfiguration(daoAccess.getDbService()).start();
    }
}