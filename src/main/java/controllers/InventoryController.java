package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
       import java.net.URL;
        import java.sql.ResultSet;
        import java.time.LocalDate;
        import java.util.Optional;
        import java.util.ResourceBundle;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.ButtonType;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableCell;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.paint.Color;
        import models.InventoryEntity;
        import util.FormValidate;
        import util.JDBC;

/**
 * FXML Controller class
 *
 * @author Waruna
 */
public class InventoryController implements Initializable {

    JDBC database = new JDBC();

    @FXML
    private TableView tbl_invent;
    @FXML
    private TableView tbl_reorder;

    @FXML
    private Button btn_update;
    @FXML
    private Button btn_search;
    @FXML
    private TextField lbl_search;
    @FXML
    private Label lbl_itemcode;
    @FXML
    private Label lbl_itemname;
    @FXML
    private Label lbl_description;
    @FXML
    private Label lbl_quantity;
    @FXML
    private Label lbl_expdate;
    @FXML
    private Label lbl_receivefrom;
    @FXML
    private Label lbl_receivedate;
    @FXML
    private TextField txt_itemcode;
    @FXML
    private TextField txt_itemname;
    @FXML
    private TextArea txt_description;
    @FXML
    private TextField txt_quantity;
    @FXML
    private DatePicker dtn_recievedate;
    @FXML
    private DatePicker dtn_expiredate;
    @FXML
    private TextField txt_recievefrom;
    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_delete;
    @FXML
    private TextField txt_minlevel;
    @FXML
    private TextField txt_unitprice;
    @FXML
    private Label lbl_supnamefield;


    int count = 0;

    @FXML
//    private TableView tbl_invent;
    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        btn_update.setVisible(false);
        btn_delete.setVisible(false);
        loadInventoryTable();
        loadReorderTable();
        generateItemID();
        tbl_invent.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            InventoryEntity entity= (InventoryEntity) obs.getValue();

            if (entity== null) {
                return;
            }

            String item_code = entity.getItem_code();
            String item_name = entity.getItem_name();
            String description = entity.getDescription();
            int qty = entity.getQuantity();
            String rec_from = entity.getReceivefrm();
            String rec_date = entity.getReceivedate();
            String exp_date = entity.getExpiredate();
            Double unit_price = entity.getUnit_price();
            int min_level = entity.getMin_level();

            txt_itemcode.setText(item_code);
            txt_itemname.setText(item_name);
            txt_description.setText(description);
            txt_quantity.setText(Integer.toString(qty));
            txt_recievefrom.setText(rec_from);
            dtn_recievedate.setValue(LocalDate.parse(rec_date));
            dtn_expiredate.setValue(LocalDate.parse(exp_date));
            txt_unitprice.setText(Double.toString(unit_price));
            txt_minlevel.setText(Integer.toString(min_level));

            btn_ok.setVisible(false);
            btn_update.setVisible(true);
            btn_delete.setVisible(true);
        });

        tbl_reorder.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            InventoryEntity entity= (InventoryEntity) obs.getValue();

            if (entity== null) {
                return;
            }

            String rec_from = entity.getReceivefrm();

            lbl_supnamefield.setText(rec_from);
        });

    }

    public void addDetails() {
        try {
            if (!checkInventoryEmptyFields() && checkNumericFields()) {

                String itemCode = txt_itemcode.getText();
                String itemName = txt_itemname.getText();
                String description = txt_description.getText();
                int quantity = Integer.parseInt(txt_quantity.getText());
                String receiveFrm = txt_recievefrom.getText();
                String receiveDate = dtn_recievedate.getValue().toString();
                String expireDate = dtn_expiredate.getValue().toString();
                int minLevel = Integer.parseInt(txt_minlevel.getText());
                Double unitPrice = Double.parseDouble(txt_unitprice.getText());

                InventoryEntity inventory = new InventoryEntity(itemCode, itemName,description,quantity ,receiveFrm , receiveDate , expireDate ,unitPrice , + minLevel);
                inventory.addNewInventory(inventory);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Alert");
                alert.setHeaderText("Item Adding Request");
                alert.setContentText("Item Details are added Successfully");
                alert.showAndWait();
                ObservableList<InventoryEntity> list = getAllInventorydata();
                tbl_invent.setItems(list);
                clearInventoryFields();
                btn_update.setVisible(false);
                generateItemID();
                tbl_reorder.setItems(getReorderInventorydata());

            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("All fields must be filled");
            alert.showAndWait();
        }
    }

    public ObservableList<InventoryEntity> getAllInventorydata() {

        JDBC j = new JDBC();
        ObservableList<InventoryEntity> inventory = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM Inventory");
            while (rset.next()) {
                String Item_code = rset.getString(1);
                String Item_name = rset.getString(2);
                String Description = rset.getString(3);
                int Quantity = rset.getInt(4);
                String receivefrm = rset.getString(5);
                String receivedate = rset.getString(6);
                String expiredate = rset.getString(7);
                Double unit_price = rset.getDouble(8);
                int min_level = rset.getInt(9);

                inventory.add(new InventoryEntity(Item_code, Item_name, Description, Quantity, receivefrm, receivedate, expiredate, unit_price, min_level));
                count++;
            }
            return inventory;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inventory;
    }

    //redorder item list
    public ObservableList<InventoryEntity> getReorderInventorydata() {

        JDBC j = new JDBC();
        ObservableList<InventoryEntity> inventory = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM Inventory where Quantity < Minqty_level");
            while (rset.next()) {
                String Item_code = rset.getString(1);
                String Item_name = rset.getString(2);
                String Description = rset.getString(3);
                int Quantity = rset.getInt(4);
                String receivefrm = rset.getString(5);
                String receivedate = rset.getString(6);
                String expiredate = rset.getString(7);
                Double unit_price = rset.getDouble(8);
                int min_level = rset.getInt(9);

                inventory.add(new InventoryEntity(Item_code, Item_name, Description, Quantity, receivefrm, receivedate, expiredate, unit_price, min_level));
                count++;
            }
            return inventory;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inventory;
    }

    //search item
    public ObservableList<InventoryEntity> getSearchInventorydata(String key) {
        JDBC j = new JDBC();
        ObservableList<InventoryEntity> inventory = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM Inventory where Item_code like '%" + key + "%' OR Item_name like'%" + key + "%' OR Receive_from like '%"+key+"%'");
            while (rset.next()) {
                String Item_code = rset.getString(1);
                String Item_name = rset.getString(2);
                String Description = rset.getString(3);
                int Quantity = rset.getInt(4);
                String receivefrm = rset.getString(5);
                String receivedate = rset.getString(6);
                String expiredate = rset.getString(7);
                Double unit_price = rset.getDouble(8);
                int min_level = rset.getInt(9);

                inventory.add(new InventoryEntity(Item_code, Item_name, Description, Quantity, receivefrm, receivedate, expiredate, unit_price, min_level));

            }
            return inventory;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inventory;
    }

    public void searchData() {
        String key = lbl_search.getText();
        ObservableList<InventoryEntity> list1 = getSearchInventorydata(key);
        tbl_invent.setItems(list1);
    }

    public void loadInventoryTable() {
        TableColumn<InventoryEntity, String> itemcode_col = new TableColumn<>("Item Code");
        itemcode_col.setCellValueFactory(new PropertyValueFactory<>("Item_code"));

        TableColumn<InventoryEntity, String> itemname_col = new TableColumn<>("Item Name");
        itemname_col.setCellValueFactory(new PropertyValueFactory<>("Item_name"));

        TableColumn<InventoryEntity, String> description_col = new TableColumn<>("Description");
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<InventoryEntity, Integer> quantity_col = new TableColumn<>("Quantity");
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<InventoryEntity, String> receivefrm_col = new TableColumn<>("Receive From");
        receivefrm_col.setCellValueFactory(new PropertyValueFactory<>("receivefrm"));

        TableColumn<InventoryEntity, String> receivedate_col = new TableColumn<>("Receive Date");
        receivedate_col.setCellValueFactory(new PropertyValueFactory<>("receivedate"));

        TableColumn<InventoryEntity, String> expiredate_col = new TableColumn<>("Expire Date");
        expiredate_col.setCellValueFactory(new PropertyValueFactory<>("Expiredate"));

        TableColumn<InventoryEntity, Integer> unit_price_col = new TableColumn<>("Unit Price");
        unit_price_col.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

        TableColumn<InventoryEntity, Integer> min_level_col = new TableColumn<>("Minimum qty Level");
        min_level_col.setCellValueFactory(new PropertyValueFactory<>("min_level"));

        tbl_invent.setItems(getAllInventorydata());
        tbl_invent.getColumns().clear();
        tbl_invent.getColumns().addAll(itemcode_col, itemname_col, description_col, quantity_col, receivefrm_col, receivedate_col, expiredate_col, unit_price_col, min_level_col);
        try {

            itemcode_col.setCellFactory((TableColumn<InventoryEntity, String> column) -> {
                return new TableCell<InventoryEntity, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (getTableRow() == null) {
                                return;
                            }

                            InventoryEntity entity = (InventoryEntity) getTableRow().getItem();

                            if (entity == null) {
                                return;
                            }

                            int qty = entity.getQuantity();
                            int minqty = entity.getMin_level();
                            setText(item);

                            if (qty < minqty) {
                                setTextFill(Color.WHITE);
                                setStyle("-fx-background-color:RED");
                            }
                        }
                    }
                };
            });

        } catch (Exception e) {

        }

    }

    public void loadReorderTable() {
        TableColumn<InventoryEntity, String> itemcode_col = new TableColumn<>("Item Code");
        itemcode_col.setCellValueFactory(new PropertyValueFactory<>("Item_code"));

        TableColumn<InventoryEntity, String> itemname_col = new TableColumn<>("Item Name");
        itemname_col.setCellValueFactory(new PropertyValueFactory<>("Item_name"));

        TableColumn<InventoryEntity, String> description_col = new TableColumn<>("Description");
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<InventoryEntity, Integer> quantity_col = new TableColumn<>("Quantity");
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<InventoryEntity, String> receivefrm_col = new TableColumn<>("Receive From");
        receivefrm_col.setCellValueFactory(new PropertyValueFactory<>("receivefrm"));

        TableColumn<InventoryEntity, String> receivedate_col = new TableColumn<>("Receive Date");
        receivedate_col.setCellValueFactory(new PropertyValueFactory<>("receivedate"));

        TableColumn<InventoryEntity, String> expiredate_col = new TableColumn<>("Expire Date");
        expiredate_col.setCellValueFactory(new PropertyValueFactory<>("Expiredate"));

        TableColumn<InventoryEntity, Integer> unit_price_col = new TableColumn<>("Unit Price");
        unit_price_col.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

        TableColumn<InventoryEntity, Integer> min_level_col = new TableColumn<>("Minimum qty Level");
        min_level_col.setCellValueFactory(new PropertyValueFactory<>("min_level"));

        tbl_reorder.setItems(getReorderInventorydata());
        tbl_reorder.getColumns().addAll(itemcode_col, itemname_col, description_col, quantity_col, receivefrm_col, receivedate_col, expiredate_col, unit_price_col, min_level_col);

    }

    public void updateInventoy() {
        try {
            if (!checkInventoryEmptyFields() && checkNumericFields()) {
                String Item_code = txt_itemcode.getText();
                String Itemname = txt_itemname.getText();
                String descript = txt_description.getText();
                int Qty = Integer.parseInt(txt_quantity.getText());
                String receivefrm = txt_recievefrom.getText();
                String receivedate = dtn_recievedate.getValue().toString();
                String Exprdate = dtn_expiredate.getValue().toString();
                int min_level = Integer.parseInt(txt_minlevel.getText());
                Double unit_price = Double.parseDouble(txt_unitprice.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Alert");
                alert.setHeaderText("Item Update Confirmation");
                alert.setContentText("Do you want to Update this Item Details ? ");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    database.setData("UPDATE inventory set Item_name='" + Itemname + "',Description='" + descript + "',Quantity=" + Qty + ", Receive_from='" + receivefrm + "', Receive_date='" + receivedate + "', Expire_date='" + Exprdate + "',Unit_price='" + unit_price + "', Minqty_level=" + min_level + " WHERE Item_code='" + Item_code + "'");
                    ObservableList<InventoryEntity> list = getAllInventorydata();
                    tbl_invent.setItems(list);

                    clearInventoryFields();
                    btn_ok.setVisible(true);
                    btn_update.setVisible(false);
                    btn_delete.setVisible(false);
                } else {
                }

                tbl_reorder.setItems(getReorderInventorydata());
                loadInventoryTable();
                generateItemID();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fields Cannot be Empty");
            alert.showAndWait();

        }

    }

    public void deleteItem() {
        String item_code = txt_itemcode.getText();
        String sql = "DELETE FROM inventory WHERE Item_code='" + item_code + "'";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Item Delete Confirmation");
        alert.setContentText("Do you want to Delete this Item Information ? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            database.setData(sql);
            ObservableList<InventoryEntity> list = getAllInventorydata();
            tbl_invent.setItems(list);
        } else {
        }
        clearInventoryFields();
        btn_delete.setVisible(false);
        btn_ok.setVisible(true);
        btn_update.setVisible(false);
        loadInventoryTable();
        tbl_reorder.setItems(getReorderInventorydata());

    }

    boolean checkInventoryEmptyFields() {
        FormValidate fv = new FormValidate();

        if (fv.isEmptyField(txt_itemcode.getText(), "Item Code")) {
            return true;
        } else if (fv.isEmptyField(txt_itemname.getText(), "Item Name")) {
            return true;
        } else if (fv.isEmptyField(txt_quantity.getText(), "Quantity")) {
            return true;
        } else if (fv.isEmptyField(txt_recievefrom.getText(), "Receive From")) {
            return true;
        } else if (fv.isEmptyField(dtn_recievedate.getValue().toString(), "Receive Date")) {
            return true;
        } else if (fv.isEmptyField(txt_unitprice.getText(), "Unit Price")) {
            return true;
        } else if (fv.isEmptyField(txt_minlevel.getText(), "Minimum Qty Level")) {
            return true;
        }

        return false;
    }

    boolean checkNumericFields() {
        FormValidate fv = new FormValidate();
        boolean status = true;
        if (!fv.isNumericAndPoint(txt_unitprice.getText(), "Unit Price ")) {
            System.out.println(" unit n" + txt_unitprice.getText() + " status : " + status);
            status = false;

        } else if (fv.isMinusValue(txt_unitprice.getText(), "Unit Price ")) {
            System.out.println(" unit m" + txt_unitprice.getText() + " status : " + status);
            status = false;
        }

        if (!fv.isNumericOnly(txt_quantity.getText(), " Quantity ")) {
            System.out.println(" quantity n" + txt_quantity.getText() + " status : " + status);
            status = false;
        } else if (fv.isMinusValue(txt_quantity.getText(), " Quantity ")) {
            System.out.println(" quantity m" + txt_quantity.getText() + " status : " + status);
            status = false;
        }

        if (!fv.isNumericOnly(txt_minlevel.getText(), " Minimum Qty level ")) {
            System.out.println(" min n" + txt_minlevel.getText() + " status : " + status);
            status = false;
        } else if (fv.isMinusValue(txt_minlevel.getText(), " Minimum Qty level ")) {
            System.out.println(" min m" + txt_minlevel.getText() + " status : " + status);
            status = false;
        }

        return status;
    }

    public void clearInventoryFields() {
        txt_itemcode.setText("");
        txt_itemname.setText("");
        txt_quantity.setText("");
        txt_description.setText("");
        txt_recievefrom.setText("");
        dtn_recievedate.setValue(null);
        dtn_expiredate.setValue(null);
        txt_minlevel.setText("");
        txt_unitprice.setText("");
        generateItemID();

        btn_delete.setVisible(false);
        btn_update.setVisible(false);
        btn_ok.setVisible(true);

    }
    //genarate code

    private void generateItemID() {
        String sql = "SELECT Item_code FROM Inventory ORDER BY Item_code DESC LIMIT 1";
        String current_itemid = "ITM0001";
        JDBC j = new JDBC();

        try {
            ResultSet rs = j.getData(sql);
            while (rs.next()) {

                current_itemid = rs.getString("Item_code");

                int number = Integer.parseInt(current_itemid.substring(3));
                number++;
                current_itemid = "ITM" + String.format("%04d", number);

            }

        } catch (Exception e) {
            //Logger.getLogger(food_management.class.getName()).log(Level.SEVERE, null, ex);
        }
        txt_itemcode.setText(current_itemid);
    }

    public void demodata() {

        txt_itemname.setText("Gift Packs");
        txt_quantity.setText("15");
        txt_description.setText("");
        txt_recievefrom.setText("99group pvt Ltd");
        txt_minlevel.setText("5");
        txt_unitprice.setText("4000.00");
    }
}
