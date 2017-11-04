package models;

import java.sql.ResultSet;
import util.JDBC;

/**
 *
 * @author Mahendra Tennakoon
 */
public class User {
    JDBC j = new JDBC();
    String userName;
    String emailAddress;
    String firstName;
    String lastName;
    String accType;

    public User() {
        this.userName = "";
        this.emailAddress = "";
        this.firstName = "";
        this.lastName = "";
        this.accType = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public User(String uname, String email, String fname, String lname, String accType) {
        this.userName = uname;
        this.emailAddress = email;
        this.firstName = fname;
        this.lastName = lname;
        this.accType = accType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieve the password stored against the user name.
     * @param user_name
     * @return
     */
    public String getAccountPassword(String user_name){
        String password = "";
        try {
            ResultSet rset = j.getData("SELECT * FROM user_account WHERE user_name = '"+user_name+"' ");
            if (rset.next()) {
                password = rset.getString(2);
                return password;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    public String getAccessPrivileges(String user_name){
        String privileges = "";
        try {
            ResultSet rset = j.getData("SELECT * FROM user_account WHERE user_name = '"+user_name+"' ");
            if (rset.next()) {
                privileges = rset.getString("access_privileges");
                return privileges;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privileges;
    }

    /**
     * Retrieve the email address stored against the user name.
     * @param user_name
     * @return
     */
    public String getAccountEmail(String user_name) {
        String email = "";
        try {
            ResultSet rset = j.getData("SELECT * FROM user_account WHERE user_name = '"+user_name+"' ");
            if (rset.next()) {
                email = rset.getString(3);
                return email;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

    /**
     * Update user account password.
     * @param username
     * @param password
     */
    public void resetPassword(String username, String password){
        try {
            j.setData("UPDATE user_account set password = '"+password+"' WHERE user_name = '"+username+"' ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
