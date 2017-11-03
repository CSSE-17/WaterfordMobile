package models;

import javax.persistence.*;

@Entity
@Table(name = "user_account", schema = "waterford_mobile", catalog = "")
public class UserEntity {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String accType;
    private String accessPrivileges;


    @Id
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "acc_type")
    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    @Basic
    @Column(name = "access_privileges")
    public String getAccessPrivileges() {
        return accessPrivileges;
    }

    public void setAccessPrivileges(String accessPrivileges) {
        this.accessPrivileges = accessPrivileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (accType != null ? !accType.equals(that.accType) : that.accType != null) return false;
        if (accessPrivileges != null ? !accessPrivileges.equals(that.accessPrivileges) : that.accessPrivileges != null)
            return false;

        return true;
    }
}
