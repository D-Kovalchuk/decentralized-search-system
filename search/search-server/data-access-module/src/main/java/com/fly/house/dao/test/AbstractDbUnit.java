package com.fly.house.dao.test;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.InputStream;

/**
 * Created by dimon on 4/26/14.
 */
public class AbstractDbUnit {

    private IDataSet dataSet;

    public void loadDataSet(Environment env) throws Exception {
        InputStream dataset = getClass().getResourceAsStream("/" + env.getProperty("dbunit.dataset"));
        dataSet = new FlatXmlDataSetBuilder().build(dataset);
    }

    public void cleanAndInsert(DataSource dataSource) throws Exception {
        DataSourceDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

}
