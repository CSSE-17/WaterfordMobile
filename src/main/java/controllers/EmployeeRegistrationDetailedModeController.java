package controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Configuration;
import models.Employee;
import util.FileUtil;
import util.FormValidate;

/**
 * FXML Controller class
 *
 * @author Mahendra Tennakoon
 */
public class EmployeeRegistrationDetailedModeController implements Initializable {

    @FXML
    private ImageView employee_photo_imageview;
    @FXML
    private TextField txt_fname;
    @FXML
    private TextField txt_lname;
    @FXML
    private TextField txt_fullname;
    @FXML
    private TextField txt_age;
    @FXML
    private DatePicker datepicker_dob;
    @FXML
    private TextField txt_nationality;
    @FXML
    private ChoiceBox choicebox_gender;
    @FXML
    private TextField txt_phone1;
    @FXML
    private TextField txt_phone2;
    @FXML
    private TextField txt_email;
    @FXML
    private TextArea txt_address;
    @FXML
    private TextField txt_employee_id;
    @FXML
    private TextField txt_nic;
    @FXML
    private TextField txt_position;
    @FXML
    private TextField txt_salary;
    @FXML
    private DatePicker datepicker_effective_from;
    @FXML
    private DatePicker datepicker_effective_to;
    @FXML
    private Button btn_terminate;

    private Path employee_photo = null;
    private Path employee_photo_curr = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Employee selectedEmployee = HumanResourcesController.selectedEmployee;
        initializeFields(selectedEmployee);
        choicebox_gender.getItems().addAll("Male", "Female");
        initializeEmployeePhoto();
        btn_terminate.setVisible(false);
    }

    /**
     * Loads the employee photo if it exists, else loads the default profile
     * image.
     */
    private void initializeEmployeePhoto() {
        String employeeID = txt_employee_id.getText();
        String[] extensions = {".png", ".jpg", ".jpeg"};
        // Initialize filepath to default employee photo.
        File filepath = new File("./src/images/employees/default_profile.png");

        Configuration conf = new Configuration();
        Properties prop = conf.readAssetsConfig();
        String src_path = prop.getProperty("employee_photo_filepath") + employeeID;

        for (String extension : extensions) {
            String filename = src_path + extension;
            File f = new File(filename);
            if (f.exists()) {
                loadEmployeePhoto(f);
                break;
            }
        }
    }

    /**
     * loads the param img into Employee photo image view.
     *
     * @param employeeId
     */
    public void loadEmployeePhoto(File file) {
        Image image = new Image(file.toURI().toString());
        employee_photo_imageview.setImage(image);
    }

    /**
     * Fill data of the selected employee into the form fields.
     *
     * @param emp
     */
    private void initializeFields(Employee emp) {
        txt_employee_id.setText(emp.getEmployee_id());
        txt_nic.setText(emp.getNIC());
        txt_position.setText(emp.getPosition());
        txt_salary.setText(Float.toString(emp.getSalary()));
        datepicker_effective_from.setValue(LocalDate.parse(emp.getEffective_date_from()));
        datepicker_effective_to.setValue(LocalDate.parse(emp.getEffective_date_to()));
        txt_fname.setText(emp.getFirst_name());
        txt_lname.setText(emp.getLast_name());
        txt_fullname.setText(emp.getFull_name());
        txt_age.setText(Integer.toString(emp.getAge()));
        datepicker_dob.setValue(LocalDate.parse(emp.getDate_of_birth()));
        txt_nationality.setText(emp.getNationality());
        txt_phone1.setText(emp.getPhone1());
        txt_phone2.setText(emp.getPhone2());
        txt_email.setText(emp.getEmail());
        txt_address.setText(emp.getAddress());
        choicebox_gender.setValue(emp.getGender());
    }

    /**
     * Deletes the selected employee in database.
     */
    public void deleteEmployee() {
        String emp_id = txt_employee_id.getText();

        Alert confDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confDialog.setTitle("Confirm action!");
        confDialog.setHeaderText("Are you sure you want to permanently delete employee " + emp_id + "?");
        Optional<ButtonType> result = confDialog.showAndWait();

        if (result.get() != ButtonType.OK) {
            return;
        }

        new Employee().deleteEmployee(emp_id);

        // check if an employee photo exists.
        if (isEmployeePhotoAvailable(emp_id)) {
            // delete employee photo;
            FileUtil fs = new FileUtil();
            fs.deleteFile(employee_photo_curr);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Employee " + emp_id + " successfully deleted!");
        alert.showAndWait();

        //close stage
        Stage stage = (Stage) txt_employee_id.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private boolean isEmployeePhotoAvailable(String emp_id) {
        String[] extensions = {".png", ".jpg", ".jpeg"};
        // Initialize filepath to default employee photo.
        File filepath = new File("./src/images/employees/default_profile.png");

        Configuration conf = new Configuration();
        Properties prop = conf.readAssetsConfig();
        String src_path = prop.getProperty("employee_photo_filepath") + emp_id;

        for (String extension : extensions) {
            String filename = src_path + extension;
            File f = new File(filename);
            if (f.exists()) {
                employee_photo_curr = f.toPath();
                return true;
            }
        }
        return false;
    }

    /**
     * updates the modified employee details in database.
     */
    public void updateEmployee() {
        if (checkEmptyFields() || !FormValidate.validateNIC(txt_nic.getText())
                || !FormValidate.isNumeric(txt_salary.getText(), "Salary")
                || !FormValidate.validateAge(txt_age.getText())
                || !FormValidate.ValidatePhoneVerbose(txt_phone1.getText(), "Phone number 1")
                || !FormValidate.ValidatePhoneVerbose(txt_phone2.getText(), "Phone number 2") || !FormValidate.validateEmailVerbose(txt_email.getText())) {
            return;
        } else {
            // Date from should be a date before date to.
            LocalDate date_from = datepicker_effective_from.getValue();
            LocalDate date_to = datepicker_effective_to.getValue();

            if (FormValidate.isAfterDate(date_from, date_to, "Date from", "date to")) {
                return;
            }
        }

        String emp_id = txt_employee_id.getText();
        String nic = txt_nic.getText();
        String position = txt_position.getText();
        float salary = Float.parseFloat(txt_salary.getText());
        String date_effective_from = datepicker_effective_from.getValue().toString();
        String date_effective_to = datepicker_effective_to.getValue().toString();
        String fname = txt_fname.getText();
        String lname = txt_lname.getText();
        String fullname = txt_fullname.getText();
        int age = Integer.parseInt(txt_age.getText());
        String dob = datepicker_dob.getValue().toString();
        String nationality = txt_nationality.getText();
        String gender = (String) choicebox_gender.getValue();
        String phone1 = txt_phone1.getText();
        String phone2 = txt_phone2.getText();
        String email = txt_email.getText();
        String address = txt_address.getText();

        Employee emp = new Employee(emp_id, nic, position, salary, fname, lname, fullname, dob, gender, age, nationality, phone1, phone2, address, email, date_effective_from, date_effective_to);
        emp.updateEmployee(emp);

        // verify if employee photo has been changed.
        if (employee_photo != null) {
            // check if an employee photo exists.
            if (isEmployeePhotoAvailable(emp_id)) {
                // delete existing employee photo;
                FileUtil fs = new FileUtil();
                fs.deleteFile(employee_photo_curr);
            }
            // copy new employee photo.
            copyEmployeePhoto(emp_id);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Employee " + emp.getEmployee_id() + " successfully updated!");
        alert.showAndWait();
    }

    private void copyEmployeePhoto(String emp_id) {
        String filetype = "";
        int i = employee_photo.toString().lastIndexOf('.');
        filetype = employee_photo.toString().substring(i + 1);

        Configuration conf = new Configuration();
        Properties prop = conf.readAssetsConfig();
        String dest_path_str = prop.getProperty("employee_photo_filepath") + emp_id + "." + filetype;
        Path dest_path = Paths.get(dest_path_str);

        FileUtil fs = new FileUtil();
        fs.copyFile(employee_photo, dest_path);
    }

    /**
     * checks whether all fields in Employee registration have been filled.
     *
     * @return
     */
    private boolean checkEmptyFields() {
        String emp_id = txt_employee_id.getText();
        String nic = txt_nic.getText();
        String position = txt_position.getText();
        String salary = txt_salary.getText();
        String date_effective_from = "";
        if (datepicker_effective_from.getValue() != null) {
            date_effective_from = datepicker_effective_from.getValue().toString();
        }
        String date_effective_to = "";
        if (datepicker_effective_to.getValue() != null) {
            date_effective_to = datepicker_effective_to.getValue().toString();
        }
        String fname = txt_fname.getText();
        String lname = txt_lname.getText();
        String fullname = txt_fullname.getText();
        String age = txt_age.getText();
        String dob = "";
        if (datepicker_dob.getValue() != null) {
            dob = datepicker_dob.getValue().toString();
        }
        String nationality = txt_nationality.getText();
        String phone1 = txt_phone1.getText();
        String address = txt_address.getText();

        FormValidate v = new FormValidate();

        if (v.isEmptyField(emp_id, "Employee ID") || v.isEmptyField(nic, "NIC")
                || v.isEmptyField(position, "Position")
                || v.isEmptyField(salary, "Salary")
                || v.isEmptyField(date_effective_from, "Date effective from")
                || v.isEmptyField(date_effective_to, "Date effective to")
                || v.isEmptyField(fname, "First name")
                || v.isEmptyField(lname, "Last name")
                || v.isEmptyField(fullname, "Full name")
                || v.isEmptyField(age, "Age") || v.isEmptyField(dob, "Date of birth")
                || v.isEmptyField(nationality, "Nationality")
                || v.isEmptyField(phone1, "Phone 01") || v.isEmptyField(address, "Address")) {
            return true;
        }
        return false;
    }

    @FXML
    private void saveEmployee(ActionEvent event) {
    }

    @FXML
    private void closeStage(ActionEvent event) {
    }

    @FXML
    private void fillSampleData(ActionEvent event) {
    }

    @FXML
    private void showFileChooserDialog(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(employee_photo_imageview.getScene().getWindow());

        String filetype = "";

        if (selectedFile == null) {
            return;
        }

        int i = selectedFile.getName().lastIndexOf('.');
        if (i > 0) {
            filetype = selectedFile.getName().substring(i + 1);
        }

        if (!filetype.equalsIgnoreCase("png") && !filetype.equalsIgnoreCase("jpg") && !filetype.equalsIgnoreCase("jpeg")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid file format.");
            alert.showAndWait();
            return;
        }

        loadEmployeePhoto(selectedFile);
        employee_photo = selectedFile.toPath();
    }
}
