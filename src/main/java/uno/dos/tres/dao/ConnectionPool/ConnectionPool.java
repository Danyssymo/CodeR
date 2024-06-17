package uno.dos.tres.dao.ConnectionPool;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class ConnectionPool {

    private static final ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }

    private BlockingQueue<Connection> connectionPoolQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private final String driverName;
    private final String url;
    private final String login;
    private final String password;
    private final int poolSize;

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.login = dbResourceManager.getValue(DBParameter.DB_LOGIN);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));

        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    // clean
    public void dispose() {
        clearConnectionQueue();
        System.out.println("Пулл выключен");
    }

    private void clearConnectionQueue() {
        try {
            closeConnestionsQueue(givenAwayConQueue);
            closeConnestionsQueue(connectionPoolQueue);
        } catch (SQLException e) {
            // logger.log
        }
    }


    private void initPoolData() throws ConnectionPoolException {
        System.out.println("Пулл включен");
        try {
            Class.forName(driverName);
            connectionPoolQueue = new ArrayBlockingQueue<>(poolSize);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, login, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionPoolQueue.add(pooledConnection);
            }


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionPoolQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Дал коннекшн");
        return connection;
    }

    public void closeConnection(ResultSet rs, PreparedStatement ps, Connection con) {
        System.out.println("Закрыл 1");
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Закрыл 2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
                System.out.println("Закрыл 3");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con != null) {
                con.close();
                System.out.println("Закрыл 4");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(PreparedStatement ps, Connection con) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnestionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;

        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    // Pattern decorator
    private class PooledConnection implements Connection {

        private Connection connection;

        public PooledConnection(Connection c) throws SQLException {

            this.connection = c;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        public void close() throws SQLException {
            System.out.println("Вызвался этот метод...");
            if (connection.isClosed()) {
                throw new SQLException("BD Connection error");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConQueue.remove(this)) {
                throw new SQLException("BD givenAwayQueue error");
            }
            if (!connectionPoolQueue.offer(this)) {
                throw new SQLException("BD connectionPoolQueue error");
            }
        }

        public void commit() throws SQLException {
            connection.commit();
        }

        public boolean isWrapperFor(Class<?> arg0) throws SQLException {
            return connection.isWrapperFor(arg0);
        }

        public <T> T unwrap(Class<T> arg0) throws SQLException {
            return connection.unwrap(arg0);
        }

        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                             int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        public void rollback() throws SQLException {
            connection.rollback();
        }

        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

    }

}
