package com.deathbydeco.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by rex on 2017/03/16.
 * <p>
 * This class is used to set up the server using the yaml file.
 */
public class MainConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("db")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("db")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
