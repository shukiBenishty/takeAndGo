package com.shuki.myapplication.backend;

import com.shuki.myapplication.datasource.DatabaseFirebase;

public class BackendFactory {
    private static final BackendFactory ourInstance = new BackendFactory();

    //static DataSource dataSourceInstance = new DatabaseList();
    static DataSource dataSourceInstance = new DatabaseFirebase();
    public static DataSource getDataSource() {return  dataSourceInstance; }

    private BackendFactory() {
    }
}
