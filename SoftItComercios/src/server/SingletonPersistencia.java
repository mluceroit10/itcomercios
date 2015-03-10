package server;

import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import persistencia.OidGenerator;

import common.RootAndIp;

public class SingletonPersistencia {

    private static SingletonPersistencia instance = new SingletonPersistencia();
    private String pmfClass = "com.triactive.jdo.PersistenceManagerFactoryImpl";
    private String driver = "com.mysql.jdbc.Driver";
    
    private String autoCreateTable = "true";
    private Properties props = new Properties();
    private PersistenceManager pm;
    private PersistenceManagerFactory pmf;

    private SingletonPersistencia(){
        super();
        props.setProperty("javax.jdo.PersistenceManagerFactoryClass",pmfClass);
        // Set the JDBC driver name.  We'll use Cloudscape 4.0.
        props.setProperty("javax.jdo.option.ConnectionDriverName", driver);
        // Set the connection URL for the Cloudscape database.
        props.setProperty("javax.jdo.option.ConnectionURL",RootAndIp.getUrlDb());
        props.setProperty("javax.jdo.option.ConnectionUserName", RootAndIp.getUserName());
        props.setProperty("javax.jdo.option.ConnectionPassword", RootAndIp.getPassword());
        props.setProperty("com.triactive.jdo.autoCreateTables", autoCreateTable);
        // Get an instance of the PersistenceManagerFactory via JDOHelper.
        try{
        	pmf = JDOHelper.getPersistenceManagerFactory(props);
        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
        try {
        	// Retrieve a PersistenceManager from the PersistenceManagerFactory.
        	pm = pmf.getPersistenceManager();
        	OidGenerator.init(pmf);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }

    public PersistenceManager getPM(){
        return pm;
    }

    public static SingletonPersistencia getInstance(){
        return instance;
    }

}
