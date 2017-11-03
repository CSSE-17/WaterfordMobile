package models;

import java.sql.ResultSet;
import util.JDBC;

/**
 *
 * @author Mahendra Tennakoon
 */
public class Employee {

    private String employee_id;
    private String NIC;
    private String position;
    private float salary;
    private String first_name;
    private String last_name;
    private String full_name;
    private String date_of_birth;
    private String gender;
    private int age;
    private String nationality;
    private String phone1;
    private String phone2;
    private String address;
    private String email;
    private String effective_date_from;
    private String effective_date_to;

    public Employee() {
        this.employee_id = "";
        this.NIC = "";
        this.position = "";
        this.salary = 0;
        this.first_name = "";
        this.last_name = "";
        this.full_name = "";
        this.date_of_birth = "";
        this.gender = "";
        this.age = 0;
        this.nationality = "";
        this.phone1 = "";
        this.phone2 = "";
        this.address = "";
        this.email = "";
        this.effective_date_from = "";
        this.effective_date_to = "";
    }

    /**
     *
     * @param employee_id
     * @param NIC
     * @param position
     * @param salary
     * @param first_name
     * @param last_name
     * @param full_name
     * @param date_of_birth
     * @param gender
     * @param age
     * @param nationality
     * @param phone1
     * @param phone2
     * @param address
     * @param email
     * @param effective_date_from
     * @param effective_date_to
     */
    public Employee(String employee_id, String NIC, String position,
                    float salary, String first_name, String last_name,
                    String full_name, String date_of_birth, String gender, int age,
                    String nationality, String phone1, String phone2, String address,
                    String email, String effective_date_from, String effective_date_to) {

        this.employee_id = employee_id;
        this.NIC = NIC;
        this.position = position;
        this.salary = salary;
        this.first_name = first_name;
        this.last_name = last_name;
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.age = age;
        this.nationality = nationality;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.address = address;
        this.email = email;
        this.effective_date_from = effective_date_from;
        this.effective_date_to = effective_date_to;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEffective_date_from() {
        return effective_date_from;
    }

    public void setEffective_date_from(String effective_date_from) {
        this.effective_date_from = effective_date_from;
    }

    public String getEffective_date_to() {
        return effective_date_to;
    }

    public void setEffective_date_to(String effective_date_to) {
        this.effective_date_to = effective_date_to;
    }

    /**
     * returns the nth order of the last registered customer.
     *
     * @return
     */
    public int getEmployeeCount() {
        int count = 0;
        JDBC j = new JDBC();

        try {
            ResultSet rset = j.getData("SELECT employee_id FROM deema_baby_fair.employee ORDER BY employee_id DESC limit 1");
            if (rset.next()) {
                String lastEmployee = rset.getString(1);
                int wall = lastEmployee.lastIndexOf("0") + 1;
//                String lastEmployeeJoinedOrder = lastEmployee.substring(wall, lastEmployee.length());
                // change this to extract the last numerical digits.
                String lastEmployeeJoinedOrder = lastEmployee.substring(lastEmployee.length() - 2, lastEmployee.length());

                count = Integer.parseInt(lastEmployeeJoinedOrder) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * save a new employee in database.
     * @param emp
     */
    public void addNewEmployee(Employee emp){
        String emp_id = emp.employee_id;
        String nic = emp.NIC;
        String position = emp.position;
        float salary = emp.salary;
        String date_effective_from = emp.effective_date_from;
        String date_effective_to = emp.effective_date_to;
        String fname = emp.first_name;
        String lname = emp.last_name;
        String fullname = emp.full_name;
        int age = emp.age;
        String dob = emp.date_of_birth;
        String nationality = emp.nationality;
        String gender = emp.gender;
        String phone1 = emp.phone1;
        String phone2 = emp.phone2;
        String email = emp.email;
        String address = emp.address;

        JDBC j = new JDBC();
        j.setData("INSERT INTO employee(employee_id, NIC, position, salary, "
                + "first_name, last_name, full_name, date_of_birth, gender, age,"
                + "nationality, phone1, phone2, address, email, "
                + "effective_date_from, effective_date_to) "
                + "VALUES('"+emp_id+"', '"+nic+"', '"+position+"', '"+salary+"',"
                + " '"+fname+"', '"+lname+"', '"+fullname+"', '"+dob+"', '"+gender+"', '"+age+"',"
                + " '"+nationality+"', '"+phone1+"', '"+phone2+"',"
                + " '"+address+"', '"+email+"', '"+date_effective_from+"',"
                + " '"+date_effective_to+"')");
    }

    /**
     * update a modified employee in database.
     * @param emp
     */
    public void updateEmployee(Employee emp){
        String emp_id = emp.employee_id;
        String nic = emp.NIC;
        String position = emp.position;
        float salary = emp.salary;
        String date_effective_from = emp.effective_date_from;
        String date_effective_to = emp.effective_date_to;
        String fname = emp.first_name;
        String lname = emp.last_name;
        String fullname = emp.full_name;
        int age = emp.age;
        String dob = emp.date_of_birth;
        String nationality = emp.nationality;
        String gender = emp.gender;
        String phone1 = emp.phone1;
        String phone2 = emp.phone2;
        String email = emp.email;
        String address = emp.address;

        JDBC j = new JDBC();
        j.setData("UPDATE employee SET employee_id='"+emp_id+"', NIC='"+nic+"',"
                + "position='"+position+"', salary='"+salary+"', "
                + "first_name='"+fname+"', last_name='"+lname+"', full_name='"+fullname+"', "
                + "date_of_birth='"+dob+"', gender='"+gender+"', age='"+age+"',"
                + "nationality='"+nationality+"', phone1='"+phone1+"', phone2='"+phone2+"', "
                + "address='"+address+"', email='"+email+"', "
                + "effective_date_from='"+date_effective_from+"', effective_date_to='"+date_effective_to+"'"
                + "WHERE employee_id='"+emp_id+"'");
    }

    /**
     * Deletes the employee by employeeID.
     * @param emp_id
     */
    public void deleteEmployee(String emp_id){
        JDBC j = new JDBC();
        j.setData("DELETE from employee WHERE employee_id='"+emp_id+"'");
    }
}
