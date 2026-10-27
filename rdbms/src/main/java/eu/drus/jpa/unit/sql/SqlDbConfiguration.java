package eu.drus.jpa.unit.sql;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import eu.drus.jpa.unit.spi.PersistenceUnitDescriptor;

public class SqlDbConfiguration {

    private Map<String, Object> dbConfig;

    public SqlDbConfiguration(final PersistenceUnitDescriptor descriptor) {
        dbConfig = descriptor.getProperties();
    }

    public static boolean isSupported(final PersistenceUnitDescriptor descriptor) {
        final Map<String, Object> dbConfig = descriptor.getProperties();

        return dbConfig.containsKey("jakarta.persistence.jdbc.driver") && dbConfig.containsKey("jakarta.persistence.jdbc.url");
    }

    public DataSource createDataSource() {
        final String driverClass = (String) dbConfig.get("jakarta.persistence.jdbc.driver");
        final String connectionUrl = (String) dbConfig.get("jakarta.persistence.jdbc.url");
        final String username = (String) dbConfig.get("jakarta.persistence.jdbc.user");
        final String password = (String) dbConfig.get("jakarta.persistence.jdbc.password");

        final BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(connectionUrl);
        ds.setMinIdle(1);
        ds.setMaxIdle(2);

        return ds;
    }
}
