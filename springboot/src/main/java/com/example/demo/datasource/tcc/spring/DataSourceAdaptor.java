package com.example.demo.datasource.tcc.spring;

import javax.sql.DataSource;

/**
 * Created by yangzz on 16/7/21.
 */
public class DataSourceAdaptor {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource= dataSource;
    }

    public DataSource getDataSource(){
        return dataSource;
    }
}
