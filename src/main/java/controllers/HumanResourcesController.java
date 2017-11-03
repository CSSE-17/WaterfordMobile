package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Employee;
import util.JDBC;

/**
 * FXML Controller class
 *
 * @author Mahendra Tennakoon
 */
public class HumanResourcesController implements Initializable {

    @FXML
    private TableView table_employees;
    @FXML
    private TextField txt_employee_filter;
    @FXML
    private CheckBox chk_employee_id;
    @FXML
    private CheckBox chk_nic;
    @FXML
    private CheckBox chk_position;
    @FXML
    private Button btn_more;
    @FXML
    private TableView table_salary_details;
    @FXML
    private TextField filter_salary_details;
    @FXML
    private ChoiceBox choicebox_salary_details;
    @FXML
    private LineChart<String, Number> line_chart_salary;
    @FXML
    private Button btn_pay_salary;
    @FXML
    private Label lbl_month;
    @FXML
    private ChoiceBox ch_prev_years;

    public static Employee selectedEmployee;
    private String salary_view = "pending";

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSalaryViewTypeChoiceBox();
        loadEmployeeTable();
        loadSalaryDetailsTable();

        table_employees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Employee e = (Employee) obs.getValue();
            selectedEmployee = e;
        });

        choicebox_salary_details.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // 0: pending, 1: paid
                int view_type = newValue.intValue();
                if (view_type == 1) {
                    btn_pay_salary.setDisable(true);
                    salary_view = "paid";
                } else {
                    btn_pay_salary.setDisable(false);
                    salary_view = "pending";
                }
                loadSalaryDetailsTable();
            }
        });
        loadSalaryChart();
        String month_and_year = LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear();
        lbl_month.setText(month_and_year);
        loadPastYears();
    }

    private void loadPastYears() {
        JDBC j = new JDBC();
        int curr_year = LocalDate.now().getYear();
        try {
            ResultSet rset = j.getData("SELECT DISTINCT YEAR(paid_date) FROM salary_details WHERE YEAR(paid_date) NOT LIKE '" + curr_year + "'");
            while (rset.next()) {
                ch_prev_years.getItems().add(rset.getInt(1));
            }
            ch_prev_years.getSelectionModel().selectLast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSalaryChart() {
        int year = LocalDate.now().getYear();
        line_chart_salary.getData().clear();
        line_chart_salary.getData().addAll(getSalaryHistoricalData(year));
    }

    public XYChart.Series<String, Number> getSalaryHistoricalData(int year) {
        JDBC j = new JDBC();
        XYChart.Series<String, Number> series_curr = new XYChart.Series<String, Number>();
        series_curr.setName(LocalDate.now().getYear() + "");
        try {
            String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
            ResultSet rset = null;

            for (String month : months) {
                rset = j.getData("SELECT SUM(amount) FROM salary_details WHERE month='" + month + "' AND YEAR(paid_date) = '" + year + "'");
                if (rset.next()) {
                    float tot_salary = rset.getFloat(1);
                    series_curr.getData().add(new XYChart.Data<String, Number>(month, tot_salary));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return series_curr;
    }

    public void compareSalaryCharts() {
        int prev_year = Integer.parseInt(ch_prev_years.getValue().toString());
        XYChart.Series<String, Number> historical_data = getSalaryHistoricalData(prev_year);
        historical_data.setName(Integer.toString(prev_year));
        line_chart_salary.getData().addAll(historical_data);
    }

    public boolean CheckSalaryPaid(String emp_no, String month) {
        try {
            JDBC j = new JDBC();
            ResultSet rset = j.getData("SELECT employee_id FROM salary_details WHERE month='" + month + "' AND employee_id='" + emp_no + "'");
            if (rset.next()) {
                System.out.println(rset.getString(1));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void paySalary() {
        Employee e = (Employee) table_salary_details.getSelectionModel().getSelectedItem();
        String emp_id = e.getEmployee_id();
        String month = LocalDate.now().getMonth().toString();
        float amount = e.getSalary();

        Date paid_date = new Date(Calendar.getInstance().getTimeInMillis());

        try {
            JDBC j = new JDBC();
            j.setData("INSERT INTO salary_details(employee_id, month, amount, paid_date) VALUES('" + emp_id + "', '" + month + "', '" + amount + "', '" + paid_date + "')");
            loadSalaryDetailsTable();
            loadSalaryChart();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * initialize the salry details choicebox.
     */
    private void loadSalaryViewTypeChoiceBox() {
        choicebox_salary_details.getItems().addAll("Pending", "Paid");
        choicebox_salary_details.setValue("Pending");
    }

    public void loadSalaryDetailsTable() {
        TableColumn<Employee, String> employee_id_col = new TableColumn<>("Employee ID");
        employee_id_col.setCellValueFactory(new PropertyValueFactory<>("employee_id"));

        TableColumn<Employee, String> nic_col = new TableColumn<>("NIC");
        nic_col.setCellValueFactory(new PropertyValueFactory<>("NIC"));

        TableColumn<Employee, String> firstname_col = new TableColumn<>("First Name");
        firstname_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<Employee, String> lastname_col = new TableColumn<>("Last Name");
        lastname_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<Employee, String> position_col = new TableColumn<>("Position");
        position_col.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Employee, Float> salary_col = new TableColumn<>("Salary");
        salary_col.setCellValueFactory(new PropertyValueFactory<>("salary"));

        ObservableList<Employee> employees = getAllEmployeeSalaryData();
        FilteredList<Employee> filteredEmployees = new FilteredList<>(employees, p -> true);

        filter_salary_details.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEmployees.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                boolean filter_emp_id = chk_employee_id.isSelected();
                boolean filter_nic = chk_nic.isSelected();
                boolean position_filter = chk_position.isSelected();

                if (employee.getEmployee_id().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getFirst_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Employee> sortedEmployees = new SortedList<>(filteredEmployees);
        sortedEmployees.comparatorProperty().bind(table_salary_details.comparatorProperty());

        table_salary_details.getColumns().clear();

        table_salary_details.setItems(sortedEmployees);
        table_salary_details.getColumns().addAll(employee_id_col, nic_col, firstname_col, lastname_col, position_col, salary_col);
    }

    /**
     * open the employee registration window as a popup with options to add and
     * delete.
     */
    public void showDetailedEmployeeView() {
        Employee selectedEmp = (Employee) table_employees.getSelectionModel().selectedItemProperty().get();

        if (selectedEmp == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Select an employee first.");
            alert.showAndWait();
            return;
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/EmployeeRegistrationDetailedMode.fxml"));
            Scene home = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(home);
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(e -> {
                table_employees.getColumns().clear();
                loadEmployeeTable();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Employee> getAllEmployeeData() {
        JDBC j = new JDBC();
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM employee");
            while (rset.next()) {
                String employee_id = rset.getString(1);
                String nic = rset.getString(2);
                String position = rset.getString(3);
                float salary = rset.getFloat(4);
                String firstname = rset.getString(5);
                String lastname = rset.getString(6);
                String fullname = rset.getString(7);
                String date_of_birth = rset.getString(8);
                String gender = rset.getString(9);
                int age = rset.getInt(10);
                String nationality = rset.getString(11);
                String phone1 = rset.getString(12);
                String phone2 = rset.getString(13);
                String address = rset.getString(14);
                String email = rset.getString(15);
                String effective_date_from = rset.getString(16);
                String effective_date_to = rset.getString(17);

                employees.add(new Employee(employee_id, nic, position, salary,
                        firstname, lastname, fullname, date_of_birth, gender,
                        age, nationality, phone1, phone2, address, email,
                        effective_date_from, effective_date_to));

            }
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    public ObservableList<Employee> getAllEmployeeSalaryData() {
        JDBC j = new JDBC();
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String month = LocalDate.now().getMonth().toString();
        try {
            ResultSet rset = null;
            if (salary_view.equalsIgnoreCase("Paid")) {
                rset = j.getData("SELECT * FROM employee WHERE employee_id IN(SELECT employee_id FROM salary_details WHERE month='" + month + "')");
            } else {
                // load only pending salary payments.
                rset = j.getData("SELECT * FROM employee WHERE employee_id NOT IN(SELECT employee_id FROM salary_details)");
            }

            while (rset.next()) {
                String employee_id = rset.getString(1);
                String nic = rset.getString(2);
                String position = rset.getString(3);
                float salary = rset.getFloat(4);
                String firstname = rset.getString(5);
                String lastname = rset.getString(6);
                String fullname = rset.getString(7);
                String date_of_birth = rset.getString(8);
                String gender = rset.getString(9);
                int age = rset.getInt(10);
                String nationality = rset.getString(11);
                String phone1 = rset.getString(12);
                String phone2 = rset.getString(13);
                String address = rset.getString(14);
                String email = rset.getString(15);
                String effective_date_from = rset.getString(16);
                String effective_date_to = rset.getString(17);

                employees.add(new Employee(employee_id, nic, position, salary,
                        firstname, lastname, fullname, date_of_birth, gender,
                        age, nationality, phone1, phone2, address, email,
                        effective_date_from, effective_date_to));
            }
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    /**
     * load the employee table.
     */
    public void loadEmployeeTable() {
        TableColumn<Employee, String> employee_id_col = new TableColumn<>("Employee ID");
        employee_id_col.setCellValueFactory(new PropertyValueFactory<>("employee_id"));

        TableColumn<Employee, String> nic_col = new TableColumn<>("NIC");
        nic_col.setCellValueFactory(new PropertyValueFactory<>("NIC"));

        TableColumn<Employee, String> position_col = new TableColumn<>("Position");
        position_col.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Employee, Float> salary_col = new TableColumn<>("Salary");
        salary_col.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Employee, String> firstname_col = new TableColumn<>("First Name");
        firstname_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<Employee, String> lastname_col = new TableColumn<>("Last Name");
        lastname_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<Employee, String> dob_col = new TableColumn<>("Date of Birth");
        dob_col.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));

        TableColumn<Employee, String> gender_col = new TableColumn<>("Gender");
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Employee, Integer> age_col = new TableColumn<>("Age");
        age_col.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Employee, String> nationality_col = new TableColumn<>("Nationality");
        nationality_col.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        TableColumn<Employee, String> phone1_col = new TableColumn<>("Phone 1");
        phone1_col.setCellValueFactory(new PropertyValueFactory<>("phone1"));

        TableColumn<Employee, String> phone2_col = new TableColumn<>("Phone 2");
        phone2_col.setCellValueFactory(new PropertyValueFactory<>("phone2"));

        TableColumn<Employee, String> email_col = new TableColumn<>("Email");
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        email_col.setMinWidth(150);

        ObservableList<Employee> employees = getAllEmployeeData();
        FilteredList<Employee> filteredEmployees = new FilteredList<>(employees, p -> true);

        txt_employee_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEmployees.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                boolean filter_emp_id = chk_employee_id.isSelected();
                boolean filter_nic = chk_nic.isSelected();
                boolean position_filter = chk_position.isSelected();

                if (employee.getFirst_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getLast_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (filter_emp_id && employee.getEmployee_id().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (filter_nic && employee.getNIC().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (position_filter && employee.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Employee> sortedEmployees = new SortedList<>(filteredEmployees);
        sortedEmployees.comparatorProperty().bind(table_employees.comparatorProperty());

        table_employees.setItems(sortedEmployees);
        table_employees.getColumns().addAll(employee_id_col, nic_col,
                position_col, salary_col, firstname_col, lastname_col, dob_col,
                gender_col, age_col, nationality_col, phone1_col, phone2_col, email_col);
    }

    /**
     * open the employee registration window as a popup.
     */
    public void gotoEmployeeRegistration() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/EmployeeRegistration.fxml"));
            Scene home = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(home);
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(e -> {
                table_employees.getColumns().clear();
                loadEmployeeTable();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
