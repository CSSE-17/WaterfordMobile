package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import models.Configuration;

/**
 *
 * @author Mahendra Tennakoon
 */
public class JDBC {

    String Driver = "com.mysql.jdbc.Driver";
    String url = "";
    String user = "";
    String password = "";

    public JDBC() {
        try {
            Configuration conf = new Configuration();
            Properties prop = conf.readDbConfig();
            user = prop.getProperty("user_name");
            password = prop.getProperty("password");
            url = prop.getProperty("url");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Connection getCon() throws Exception {
        Class.forName(Driver);
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }

    public void setData(String sql) {
        try {
            Statement state = getCon().createStatement();
            state.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet getData(String sql) throws Exception {
        Statement state = getCon().createStatement();
        ResultSet rs = state.executeQuery(sql);
        return rs;
    }
    public boolean setAddData(String sql) {
        try {
            Statement state = getCon().createStatement();
            if(state.executeUpdate(sql)>0)
                return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
