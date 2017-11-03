package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.SupplierDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.SupplierEntity;
import util.FormValidate;
import util.JDBC;

/**
 * FXML Controller class
 *
 * @author Waruna
 */
public class SupplierController implements Initializable {
    JDBC db = new JDBC();

    @FXML
    private TableView tbl_supplier;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_search;
    @FXML
    private TextField lbl_search;
    @FXML
    private Label lbl_supid;
    @FXML
    private Label lbl_supname;
    @FXML
    private Label lbl_description;
    @FXML
    private Label lbl_address;
    @FXML
    private Label lbl_joindate;
    @FXML
    private Label lbl_tpnumber;
    @FXML
    private Label lbl_email;
    @FXML
    private TextField txt_supid;
    @FXML
    private TextField txt_supname;
    @FXML
    private TextArea txt_supdescription;
    @FXML
    private TextField txt_address;
    @FXML
    private DatePicker dtn_joindate;
    @FXML
    private TextField txt_tpnumber;
    @FXML
    private TextField txt_email;
    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_delete;
    @FXML
    private Label lbl_bank;
    @FXML
    private Label lbl_branch;
    @FXML
    private Label lbl_accntno;
    @FXML
    private TextField txt_bank;
    @FXML
    private TextField txt_branch;
    @FXML
    private TextField txt_accntno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSupplierTable();
        btn_update.setVisible(false);
        btn_delete.setVisible(false);
        btn_delete.setVisible(false);
        generateSupplierID();
        tbl_supplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            SupplierEntity entity= (SupplierEntity) obs.getValue();

            if (entity== null) {
                return;
            }

            String supid = entity.getSupplierId();
            String item_name = entity.getSupplierName();
            String description = entity.getDescription();
            String address = entity.getAddress();
            String Tpnumber = entity.getTpnumber();
            String email = entity.getEmail();
            String datejoin = entity.getDateJoined();
            String bank = entity.getBank();
            String branch = entity.getBranch();
            String accntno = entity.getAccntNo();

            txt_supid.setText(supid);
            txt_supname.setText(item_name);
            txt_supdescription.setText(description);
            txt_address.setText(address);
            txt_tpnumber.setText(Tpnumber);
            txt_email.setText(email);
            dtn_joindate.setValue(LocalDate.parse(datejoin));
            txt_bank.setText(bank);
            txt_branch.setText(branch);
            txt_accntno.setText(accntno);

            btn_ok.setVisible(false);
            btn_update.setVisible(true);
            btn_delete.setVisible(true);
        });
    }

    public void addSupplier() {
        try {
            if (!checkInventoryEmptyFields() && CheckPhone() && Checkemail()) {
                String Sup_id = txt_supid.getText();
                String Sup_name = txt_supname.getText();
                String Description = txt_supdescription.getText();
                String Address = txt_address.getText();
                String Tpnumber = txt_tpnumber.getText();
                String Email = txt_email.getText();
                String Date_joined = dtn_joindate.getValue().toString();
                String Bank = txt_bank.getText();
                String Branch = txt_branch.getText();
                String Accnt_no = txt_accntno.getText();

                SupplierDAO supplierDAO = new SupplierDAO();
                supplierDAO.setup();
                SupplierEntity supplier = new SupplierEntity();

                supplier.setSupplierId(Sup_id);
                supplier.setSupplierName(Sup_name);
                supplier.setDescription(Description);
                supplier.setAddress(Address);
                supplier.setTpnumber(Tpnumber);
                supplier.setEmail(Email);
                supplier.setDateJoined(Date_joined);
                supplier.setBank(Bank);
                supplier.setBranch(Branch);
                supplier.setAccntNo(Accnt_no);

                supplierDAO.create(supplier);
                supplierDAO.exit();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Alert");
                alert.setHeaderText("Supplier Adding Request");
                alert.setContentText("Supplier Information is added Successfully");
                alert.showAndWait();
                ObservableList<SupplierEntity> list = getAllSupplierdata();
                tbl_supplier.setItems(list);
                clearSupplierFields();
                btn_update.setVisible(false);
                generateSupplierID();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fields Cannot be Empty");
            alert.showAndWait();

        }

    }

    public ObservableList<SupplierEntity> getAllSupplierdata() {
        JDBC j = new JDBC();
        ObservableList<SupplierEntity> supplier = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM supplier");
            while (rset.next()) {
                String supid = rset.getString(1);
                String supname = rset.getString(2);
                String description = rset.getString(3);
                String address = rset.getString(4);
                String tpNumber = rset.getString(5);
                String email = rset.getString(6);
                String dateJoin = rset.getString(7);
                String bank = rset.getString(8);
                String branch = rset.getString(9);
                String accnt_no = rset.getString(10);

                supplier.add(new SupplierEntity(supid, supname, description, address, tpNumber, email, dateJoin, bank, branch, accnt_no));

            }
            return supplier;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return supplier;
    }

    public void loadSupplierTable() {
        TableColumn<SupplierEntity, String> supID_col = new TableColumn<>("Supplier ID");
        supID_col.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        TableColumn<SupplierEntity, String> supname_col = new TableColumn<>("Supplier Name");
        supname_col.setCellValueFactory(new PropertyValueFactory<>("supplierName"));

        TableColumn<SupplierEntity, String> description_col = new TableColumn<>("Description");
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<SupplierEntity, String> address_col = new TableColumn<>("address");
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<SupplierEntity, String> tpnum_col = new TableColumn<>("TP Number");
        tpnum_col.setCellValueFactory(new PropertyValueFactory<>("tpnumber"));

        TableColumn<SupplierEntity, String> email_col = new TableColumn<>("Email");
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<SupplierEntity, String> datejoin_col = new TableColumn<>("Date Joined");
        datejoin_col.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));

        TableColumn<SupplierEntity, String> bank_col = new TableColumn<>("Bank");
        bank_col.setCellValueFactory(new PropertyValueFactory<>("bank"));

        TableColumn<SupplierEntity, String> branch_col = new TableColumn<>("Branch");
        branch_col.setCellValueFactory(new PropertyValueFactory<>("branch"));

        TableColumn<SupplierEntity, String> accntno_col = new TableColumn<>("Account No");
        accntno_col.setCellValueFactory(new PropertyValueFactory<>("accntNo"));

        tbl_supplier.setItems(getAllSupplierdata());
        tbl_supplier.getColumns().addAll(supID_col, supname_col, description_col, address_col, tpnum_col, email_col, datejoin_col, bank_col, branch_col, accntno_col);
    }

    public void updateSupplier() {
        try {
            String Sup_id = txt_supid.getText();
            String Sup_name = txt_supname.getText();
            String Description = txt_supdescription.getText();
            String Address = txt_address.getText();
            String Tpnumber = txt_tpnumber.getText();
            String Email = txt_email.getText();
            String Date_joined = dtn_joindate.getValue().toString();
            String Bank = txt_bank.getText();
            String Branch = txt_branch.getText();
            String Accnt_no = txt_accntno.getText();

            if (!checkInventoryEmptyFields() && CheckPhone() && Checkemail()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Alert");
                alert.setHeaderText("Supplier Update Confirmation");
                alert.setContentText("Do you want to Update this Supplier Information ? ");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    db.setData("UPDATE supplier set Supplier_name='" + Sup_name + "',Description='" + Description + "',Address='" + Address + "', Tpnumber='" + Tpnumber + "', Email='" + Email + "', Date_joined='" + Date_joined + "', Bank='" + Bank + "', Branch='" + Branch + "', Accnt_no='" + Accnt_no + "' WHERE Supplier_ID='" + Sup_id + "'");
                    ObservableList<SupplierEntity> list = getAllSupplierdata();
                    tbl_supplier.setItems(list);
                    clearSupplierFields();
                    btn_ok.setVisible(true);
                    btn_update.setVisible(false);
                    btn_delete.setVisible(false);
                } else {
                }
            }

            generateSupplierID();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fields Cannot be Empty");
            alert.showAndWait();
        }

    }

    public void deleteItem() {
        String supid = txt_supid.getText();
        String sql = "DELETE FROM supplier WHERE Supplier_ID='" + supid + "'";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Supplier Delete Confirmation");
        alert.setContentText("Do you want to Delete this Supplier Information ? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData(sql);
            ObservableList<SupplierEntity> list = getAllSupplierdata();
            tbl_supplier.setItems(list);
        } else {

        }
        btn_delete.setVisible(false);
        btn_ok.setVisible(true);
        btn_update.setVisible(false);
        clearSupplierFields();
        generateSupplierID();
    }

    boolean checkInventoryEmptyFields() {
        FormValidate fv = new FormValidate();

        if (fv.isEmptyField(txt_supid.getText(), "Supplier ID")) {
            return true;
        } else if (fv.isEmptyField(txt_supname.getText(), "Supplier Name")) {
            return true;
        } else if (fv.isEmptyField(txt_address.getText(), "Address")) {
            return true;
        } else if (fv.isEmptyField(txt_tpnumber.getText(), "TP Number")) {
            return true;
        } else if (fv.isEmptyField(dtn_joindate.getValue().toString(), "Join Date")) {
            return true;
        } else if (fv.isEmptyField(txt_bank.getText(), "Bank")) {
            return true;
        } else if (fv.isEmptyField(txt_branch.getText(), "Branch")) {
            return true;
        } else if (fv.isEmptyField(txt_accntno.getText(), "Account No")) {
            return true;
        }

        return false;
    }

    boolean CheckPhone() {

        FormValidate fv = new FormValidate();

        if (!fv.ValidatePhone(txt_tpnumber.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Phone no.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    boolean Checkemail() {
        FormValidate fv = new FormValidate();

        if (!fv.validateEmail(txt_email.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void clearSupplierFields() {
        txt_supid.setText("");
        txt_supname.setText("");
        txt_supdescription.setText("");
        txt_tpnumber.setText("");
        txt_address.setText("");
        txt_email.setText("");
        dtn_joindate.setValue(null);
        txt_accntno.setText("");
        txt_bank.setText("");
        txt_branch.setText("");
        generateSupplierID();

        btn_delete.setVisible(false);
        btn_update.setVisible(false);
        btn_ok.setVisible(true);

    }

    //search suppier
    public ObservableList<SupplierEntity> getSearchSuppierdata(String key) {
        JDBC j = new JDBC();
        ObservableList<SupplierEntity> supplier = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM Supplier where Supplier_ID like '%" + key + "%' OR Supplier_name like '%" + key + "%' OR Tpnumber like '%" + key + "%'");
            while (rset.next()) {
                String SupID = rset.getString(1);
                String Sup_name = rset.getString(2);
                String Supdescription = rset.getString(3);
                String Supaddress = rset.getString(4);
                String tpnum = rset.getString(5);
                String email = rset.getString(6);
                String Datejoin = rset.getString(7);
                String bank = rset.getString(8);
                String branch = rset.getString(9);
                String Accntnum = rset.getString(10);

                supplier.add(new SupplierEntity(SupID, Sup_name, Supdescription, Supaddress, tpnum, email, Datejoin, bank, branch, Accntnum));
            }
            return supplier;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return supplier;
    }

    public void searchsupplierData() {
        String key = lbl_search.getText();
        ObservableList<SupplierEntity> list1 = getSearchSuppierdata(key);
        tbl_supplier.setItems(list1);
    }

    //genarate ID
    private void generateSupplierID() {
        String sql = "SELECT Supplier_ID FROM Supplier ORDER BY Supplier_ID DESC LIMIT 1";
        String current_supid = "SUP0001";
        JDBC j = new JDBC();
        //ObservableList<SupplierEntity> supplier = FXCollections.observableArrayList();
        //lbllItemCode.setText("ITM0001");
        try {
            ResultSet rs = j.getData(sql);
            while (rs.next()) {

                current_supid = rs.getString("Supplier_ID");

                int number = Integer.parseInt(current_supid.substring(3));
                number++;
                current_supid = "SUP" + String.format("%04d", number);

            }

        } catch (Exception e) {
            //Logger.getLogger(food_management.class.getName()).log(Level.SEVERE, null, ex);
        }
        txt_supid.setText(current_supid);
    }

    public void demosupdata() {

        txt_supname.setText("Grand Toys");
        txt_supdescription.setText("");
        txt_tpnumber.setText("0112485724");
        txt_address.setText("Malatha Road, Kandana");
        txt_email.setText("grnd@gmail.com");
        txt_accntno.setText("021457845124");
        txt_bank.setText("Nations Trust Bank");
        txt_branch.setText("Kandana");

    }
}

