package by.larchanka.tiptopcleaning.connection;

import com.mysql.cj.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();

    private static final int DEFAULT_POLL_SIZE = 5;
    private String poolSizeText = ConnectionParameter.getPollSize();
    private int pollSize = !StringUtils.isNullOrEmpty(poolSizeText) ? Integer.parseInt(poolSizeText) : DEFAULT_POLL_SIZE;
    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> reservedConnections;

    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public void createPoll() throws ConnectionPoolException {
        availableConnections = new LinkedBlockingQueue<>(pollSize);
        reservedConnections = new LinkedBlockingQueue<>(pollSize);

        try {
            Class.forName(ConnectionParameter.getDriver());

            for (int i = 0; i < pollSize; i++) {
                try {
                    Connection connection = DriverManager.getConnection(ConnectionParameter.getURL(), ConnectionParameter.getDBLogin(), ConnectionParameter.getDBPassword());
                    availableConnections.offer(new ProxyConnection(connection));
                } catch (SQLException e) {
                    logger.fatal(e);
                    throw new ConnectionPoolException(e);
                }
            }
        } catch (ClassNotFoundException e) {
            logger.fatal(e);
            throw new ConnectionPoolException(e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;

        try {
            connection = availableConnections.take();
            reservedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error(e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    void returnConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed() && connection instanceof ProxyConnection) {
            if (!connection.getAutoCommit()) {
                logger.error("Attempt to return connection with disabled auto-commit {}", connection);
                connection.rollback();
                connection.setAutoCommit(true);
            }
            availableConnections.offer((ProxyConnection) connection);
            reservedConnections.remove(connection);
        } else {
            Thread.currentThread().interrupt();
            logger.error("Release not ProxyConnection");
            throw new ConnectionCouldNotBeReturnedException();
        }
    }

    public void destroyPool() throws ConnectionPoolException {
        for (int i = 0; i < availableConnections.size(); i++) {
            try {
                ProxyConnection proxyConnection = availableConnections.take();
                proxyConnection.realClose();
            } catch (InterruptedException | SQLException e) {
                logger.error(e);
                throw new ConnectionPoolException(e);
            }
        }
    }
}
