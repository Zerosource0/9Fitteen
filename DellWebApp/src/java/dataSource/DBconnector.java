package dataSource;

/**
 *
 * @author Andreas
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

class DBConnector
{
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat";
    private static String id = "cphjb186";
    private static String pw = "cphjb186";
    
    private Connection con;
    private DataSource dataSource;
    
    private static DBConnector instance;
    
    private DBConnector()
    {
        try
        {
            Class.forName(driver);
            dataSource = setupDataSource(URL);
		  					     
        } catch (Exception e)
        {
            System.out.println("error in DBConnector.getConnection()");
            System.out.println(e);
        }
    }
    
    private static DataSource setupDataSource(String connectURI) {
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, id, pw);
        
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);
        
        return dataSource;      
    }
    
    static DBConnector getInstance()
    {
        if (instance == null)
            instance = new DBConnector();
        return instance;
    }
    
    Connection getConnection()
    {
        try {
            con = dataSource.getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
      return con;
    }
}
