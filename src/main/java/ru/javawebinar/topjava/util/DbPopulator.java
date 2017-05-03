package ru.javawebinar.topjava.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DbPopulator extends ResourceDatabasePopulator {
    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    private final DataSource dataSource;

    public DbPopulator(String scriptLocation, DataSource dataSource) {
        super(RESOURCE_LOADER.getResource(scriptLocation));
        this.dataSource = dataSource;
    }

    public void execute() {
        DatabasePopulatorUtils.execute(this, dataSource);
    }
}
