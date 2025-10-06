package com.nao.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * HikariCP DataSource configuration. Edit credentials or set environment variables.
 */
public class DatabaseConfig {

    private static HikariDataSource ds;

    public static DataSource getDataSource() {
        if (ds == null) {
            HikariConfig cfg = new HikariConfig();
            String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/DB_process_automation");
            String user = System.getenv().getOrDefault("DB_USER", "postgres");
            String pass = System.getenv().getOrDefault("DB_PASS", "Millon123");

            cfg.setJdbcUrl(url);
            cfg.setUsername(user);
            cfg.setPassword(pass);
            cfg.setMaximumPoolSize(5);
            cfg.setMinimumIdle(1);
            cfg.setPoolName("scholar-pool");
            cfg.addDataSourceProperty("cachePrepStmts", "true");
            cfg.addDataSourceProperty("prepStmtCacheSize", "250");
            cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            ds = new HikariDataSource(cfg);
        }
        return ds;
    }
}
