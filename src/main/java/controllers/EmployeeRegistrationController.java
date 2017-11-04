package controllers;


import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class EmployeeRegistrationController implements Initializable {

    @FXML
    private ImageView employee_photo_imageview;
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

    private Path employee_photo = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmployeePhoto();
        generateEmployeeID();
        loadGenderChoiceBox();
    }

    /**
     * load default profile picture.
     */
    public void loadEmployeePhoto() {
        File imageFile = new File("C:\\Users\\VTENNM1\\IdeaProjects\\WaterfordMobile\\src\\main\\resources\\images\\employees\\default_profile.png");
        Image image = new Image(imageFile.toURI().toString());
        employee_photo_imageview.setImage(image);
    }

    /**
     * loads the param img into Employee photo image view.
     *
     * @param employeeId
     */
    public void loadEmployeePhoto(String filepath) {
        File imageFile = new File(filepath);
        Image image = new Image(imageFile.toURI().toString());
        employee_photo_imageview.setImage(image);
    }

    /**
     * open a new file chooser dialog.
     */
    @FXML
    public void showFileChooserDialog() {
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

        loadEmployeePhoto(selectedFile.toPath().toString());
        employee_photo = selectedFile.toPath();
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
     * generate a new employee id and display it on relevant text field.
     */
    public void generateEmployeeID() {
        String emp_id = "";
        Calendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR) % 100;
        int empCount = new Employee().getEmployeeCount();
        int curr_len = (year + "" + empCount).length();
        int padding_len = 10 - curr_len;

        emp_id = emp_id + year;
        for (int i = 0; i < padding_len; i++) {
            emp_id = emp_id + 0;
        }
        emp_id = emp_id + empCount;

        txt_employee_id.setText(emp_id);
    }

    /**
     * closes employee registration stage.
     */
    @FXML
    public void closeStage() {
        Stage stage = (Stage) employee_photo_imageview.getScene().getWindow();
        stage.close();
    }

    /**
     * saves the newly registered employee in database.
     */
    @FXML
    public void saveEmployee() {
        //validate form inputs
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
        emp.addNewEmployee(emp);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Employee " + emp_id + " successfully added!");
        alert.showAndWait();

        // Copy employee photo.
        if (employee_photo != null) {
            copyEmployeePhoto(emp_id);
        }

        //close stage
        Stage stage = (Stage) txt_employee_id.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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

    /**
     * initialize the gender choicebox.
     */
    private void loadGenderChoiceBox() {
        choicebox_gender.getItems().addAll("Male", "Female");
        choicebox_gender.setValue("Male");
    }

    /**
     * tests
     */
    @FXML
    public void fillSampleData() {
        txt_nic.setText("787878789V");
        txt_position.setText("Cashier");
        txt_salary.setText("45000.00");
        datepicker_effective_from.setValue(LocalDate.now());
        datepicker_effective_to.setValue(LocalDate.parse("2019-04-17"));
        txt_fname.setText("Kamal");
        txt_lname.setText("Perera");
        txt_fullname.setText("Kamal aponso perera");
        txt_age.setText("45");
        datepicker_dob.setValue(LocalDate.parse("1989-04-17"));
        txt_nationality.setText("Sinhalese");
        txt_phone1.setText("0712323234");
        txt_phone2.setText("0722345641");
        txt_email.setText("Kamal@gmail.com");
        txt_address.setText("no78, \n kandy, \n sri lanka");
    }
}
