package com.betacom.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.betacom.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SQLConfiguration {

    // Istanza unica — creata una volta sola e riutilizzata ovunque
    private static SQLConfiguration instance;
    private static final Properties sql     = new Properties();
    private static final Properties queries = new Properties();
    private Connection connection;

    private SQLConfiguration() {}

    public static SQLConfiguration getInstance() {
        if (instance == null) {
            instance = new SQLConfiguration();
            loadProperties();
        }
        return instance;
    }

    // Carica sql.properties e query.properties dal classpath
    private static void loadProperties() {
        try {
            load(sql,     "sql.properties");
            load(queries, "query.properties");
            log.info("Configuration loaded successfully");
        } catch (Exception e) {
            throw new AppException("Failed to load configuration: " + e.getMessage());
        }
    }

    private static void load(Properties props, String file) throws Exception {
        InputStream in = SQLConfiguration.class.getClassLoader().getResourceAsStream(file);
        if (in == null) throw new AppException(file + " not found in classpath");
        props.load(in);
    }

    // Apre una nuova connessione oppure restituisce quella già aperta
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(sql.getProperty("driver"));
                connection = DriverManager.getConnection(
                    sql.getProperty("url"),
                    sql.getProperty("user"),
                    sql.getProperty("pwd")
                );
                log.info("Connection opened → {}", sql.getProperty("url"));
            }
            return connection;
        } catch (Exception e) {
            throw new AppException("Connection failed: " + e.getMessage());
        }
    }

    // Restituisce la query SQL associata alla chiave in query.properties
    public String getQuery(String key) {
        return queries.getProperty(key);
    }

    public void closeConnection() {
        try { if (connection != null) { connection.close(); connection = null; } }
        catch (Exception e) { throw new AppException(e.getMessage()); }
    }

    // Gestione transazioni
    public void setTransaction() { setAutoCommit(false); }
    public void setAutoCommit()  { setAutoCommit(true);  }

    private void setAutoCommit(boolean value) {
        try { getConnection().setAutoCommit(value); }
        catch (Exception e) { throw new AppException(e.getMessage()); }
    }

    public void commit() {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true); // ripristina autocommit dopo ogni transazione
        }
        catch (Exception e) { throw new AppException(e.getMessage()); }
    }

    public void rollback() {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true); // ripristina autocommit anche dopo un rollback
        }
        catch (Exception e) { throw new AppException(e.getMessage()); }
    }
}
