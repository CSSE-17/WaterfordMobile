/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Property;
import models.User;
import models.propertyModel;
import util.JDBC;

/**
 * FXML Controller class
 *
 * @author Pasan
 */
public class InfrastructureManagementController implements Initializable {

    @FXML
    private Button btn_delete_property;
    @FXML
    private TextField txt_property_filter;
    @FXML
    private CheckBox chk_eid;
    @FXML
    private CheckBox chk_wid;
    @FXML
    private TextField txt_p_id;
    @FXML
    private TextField txt_e_id;
    @FXML
    private TextField txt_w_id;
    @FXML
    private TextField txt_rent;
    @FXML
    private Button btn_add_new;
    @FXML
    private Button btn_update_confirm;
    @FXML
    private Button btn_clear;
    @FXML
    private Button btn_calc;
    @FXML
    private ChoiceBox<?> cmb_select_property;
    @FXML
    private TextField txt_wid;
    @FXML
    private TextField txt_eid;
    @FXML
    private TextField txt_pid;
    @FXML
    private ComboBox cmb_month;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_nic;
    @FXML
    private TextField txt_rental;
    @FXML
    private TextField txt_pending;
    @FXML
    private TextField txt_e_pay;
    @FXML
    private TextField txt_w_pay;
    @FXML
    private TextField txt_total;
    @FXML
    private TextField txt_agree_id;
    @FXML
    private Button btn_print_bill;
    @FXML
    private TextField txt_agree;
    @FXML
    private DatePicker datepick_start_date;
    @FXML
    private DatePicker datepick_end_date;
    @FXML
    private ComboBox chk_pid;
    @FXML
    private Button btn_browse;
    @FXML
    private TextField txt_name_add;
    @FXML
    private TextField txt_nic_add;
    @FXML
    private TextField txt_contact_add;
    @FXML
    private TextField txt_email_add;
    @FXML
    private TextField txt_nature_add;
    @FXML
    private Button btn_add_rent;
    @FXML
    private Button btn_update_rent;
    @FXML
    private Button btn_delete_rent;
    @FXML
    private Button btn_clear_rent;
    @FXML
    private TableView table_property;
    @FXML
    private TableView test_table;
    @FXML
    private Label lbl_p_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadPropertiesTable();
        monthChoiceBox();
        try {
            ld_property();

        } catch (Exception e) {
        }

        table_property.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            propertyModel prop = (propertyModel) obs.getValue();

            updateProperty(prop);
        });
    }

    /**
     * Load Property Table
     */

    public void loadPropertiesTable() {

        TableColumn<propertyModel, Integer> prop_id_col = new TableColumn<>("Property ID");
        prop_id_col.setCellValueFactory(new PropertyValueFactory<>("propertyId"));

        TableColumn<propertyModel, Integer> elec_bill_id_col = new TableColumn<>("Electricity Bill ID");
        elec_bill_id_col.setCellValueFactory(new PropertyValueFactory<>("electricityBill"));

        TableColumn<propertyModel, Integer> water_bill_id_col = new TableColumn<>("Water Bill ID");
        water_bill_id_col.setCellValueFactory(new PropertyValueFactory<>("waterBill"));

        TableColumn<propertyModel, Float> rental_col = new TableColumn<>("Rental");
        rental_col.setCellValueFactory(new PropertyValueFactory<>("rentalValue"));

        table_property.getColumns().clear();

        ObservableList<propertyModel> properties = getAllPropertyData();
        FilteredList<propertyModel> filteredProperties = new FilteredList<>(properties, p -> true);

        txt_property_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProperties.setPredicate(prop -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (Integer.toString(prop.getPropertyId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<propertyModel> sortedProps = new SortedList<>(filteredProperties);
        sortedProps.comparatorProperty().bind(table_property.comparatorProperty());

        table_property.setItems(sortedProps);
        table_property.getColumns().addAll(prop_id_col, elec_bill_id_col, water_bill_id_col, rental_col);
    }

    public ObservableList<propertyModel> getAllPropertyData() {
        ObservableList<propertyModel> properties = FXCollections.observableArrayList();

        JDBC j = new JDBC();
        try {
            ResultSet rset = j.getData("SELECT * FROM property");
            while (rset.next()) {
                properties.add(new propertyModel(rset.getInt(1), rset.getInt(2), rset.getInt(3), rset.getFloat(4)));
            }
        } catch (Exception e) {
        }

        return properties;
    }

    /**
     * Add New Property
     */
    public void addProperty() {
        int eBillId = Integer.parseInt(txt_e_id.getText());
        int wBillId = Integer.parseInt(txt_w_id.getText());
        float rental = Float.parseFloat(txt_rent.getText());

        Property prop = new Property(eBillId, wBillId, rental);
        prop.addNewProperty(prop);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful");
        alert.setHeaderText("New Property Added Successfully");
        alert.showAndWait();

        loadPropertiesTable();
        clear();

    }

    /**
     * load details to be updated to fields
     */
    public void updateProperty(propertyModel x) {
        lbl_p_id.setText(x.getPropertyId() + "");
        txt_e_id.setText(x.getElectricityBill() + "");
        txt_w_id.setText(x.getWaterBill() + "");
        txt_rent.setText(x.getRentalValue() + "");
    }

    /**
     * update database
     */
    public void updateConfirm() {
        try {
            int pid = Integer.parseInt(lbl_p_id.getText());
            int eBillId = Integer.parseInt(txt_e_id.getText());
            int wBillId = Integer.parseInt(txt_w_id.getText());
            float rental = Float.parseFloat(txt_rent.getText());

//            Property prop = new Property(eBillId, wBillId, rental);
//            prop.updateProperty(prop);
            JDBC j = new JDBC();
            j.setData("UPDATE property SET eBillId='" + eBillId + "', wBillId='" + wBillId + "', rental='" + rental + "' "
                    + "WHERE pid='" + pid + "' ");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Update Successfully");
            alert.showAndWait();

            loadPropertiesTable();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * clear data in fields
     */

    public void clear() {
        lbl_p_id.setText("");
        txt_e_id.setText("");
        txt_w_id.setText("");
        txt_rent.setText("");
    }

    /**
     * delete selected row
     */

    public void delete() {

        int pid = Integer.parseInt(lbl_p_id.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do You Really Want to Delete the Selected Property?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                JDBC j = new JDBC();
                j.setData("DELETE from property where pid='" + pid + "' ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        loadPropertiesTable();
    }

    private void monthChoiceBox() {
        cmb_month.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cmb_month.setValue("select month");
    }

    private void ld_property() throws Exception {
        JDBC j = new JDBC();
        try {
            ResultSet rset = j.getData("SELECT pid FROM property");
            while (rset.next()) {

                chk_pid.getItems().add(rset.getInt(1));
            }

        } catch (Exception e) {
        }

    }

    /**
     * Clear Agreement
     */
    public void clearAgreement() {

        txt_agree.setText("");
        datepick_start_date.setPromptText("select month");
        datepick_end_date.setPromptText("select month");
        chk_pid.setPromptText("");
        txt_name_add.setText("");
        txt_nic_add.setText("");
        txt_contact_add.setText("");
        txt_email_add.setText("");
        txt_nature_add.setText("");

    }

    /**
     * Add Agreement
     */
    public void addAgreement() {

        int pid = Integer.parseInt((String) chk_pid.getValue());
        String startDate = datepick_start_date.getValue().toString();
        String endDate = datepick_end_date.getValue().toString();
        String name = txt_name_add.getText();
        String nic = txt_nic_add.getText();
        String contact = txt_contact_add.getText();
        String email = txt_email_add.getText();
        String nature = txt_nature_add.getText();

        try {
            JDBC j = new JDBC();
            j.setData("INSERT into agreement(propId,startDate,endDate,name,nic,contact,email.nature) VALUES ('"+pid+"', '"+startDate+"', '"+endDate+"', '"+name+"', '"+nic+"', '"+contact+"', '"+nature+"')");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Agreement Added Successfully");
            alert.showAndWait();
        } catch (Exception e) {
        }

    }
    public void update(){

        int pid = Integer.parseInt((String) chk_pid.getValue());

        JDBC j = new JDBC();
        try {
            ResultSet rset = j.getData("SELECT * FROM property WHERE propId = '"+pid+"'");
            while (rset.next()) {
                txt_agree.setText(rset.getInt(1) + "");

            }
        } catch (Exception e) {
        }
    }

}
