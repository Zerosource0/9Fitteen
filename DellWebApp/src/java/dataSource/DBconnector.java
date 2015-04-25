package dataSource;

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

public class DBconnector
{

    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat";
    private static String id = "cphjb186";
    private static String pw = "cphjb186";
    
    private Connection con;
    private DataSource dataSource;

    //-- Singleton ---- 
    private static DBconnector instance;
    
    private DBconnector()
    {
        try
        {
            Class.forName(driver);
            dataSource = setupDataSource(URL);
            //con = DriverManager.getConnection(URL, id, pw);   
		  					     
        } catch (Exception e)
        {
            System.out.println("error in DBConnector.getConnection()");
            System.out.println(e);
        }
    }
    
    public static DataSource setupDataSource(String connectURI) {
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, id, pw);
        
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);
        
        return dataSource;      
    }
    
    public static DBconnector getInstance()
    {
        if (instance == null)
            instance = new DBconnector();
        return instance;
    }
    
    public Connection getConnection()
    {
        try {
            con = dataSource.getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBconnector.class.getName()).log(Level.SEVERE, null, ex);
        }
      return con;
    }
}
