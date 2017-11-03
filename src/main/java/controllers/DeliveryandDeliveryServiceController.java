package controllers;

import static com.sun.deploy.config.JREInfo.clear;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.DeliveryModel;
import models.Employee;
import models.ServiceModel;
import models.delivery;
import util.FormValidate;
import util.JDBC;

/**
 * FXML Controller class
 *
 * @author Waruni
 */
public class DeliveryandDeliveryServiceController implements Initializable {

    @FXML
    private TextField txt_delivery_id;
    @FXML
    private TextField txt_order_date;
    @FXML
    private TextField txt_delivery_cost;
    @FXML
    private TextField txt_quantity;
    @FXML
    private TextField txt_delivery_filter;
    @FXML
    private TextField txt_service_id;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_address;
    @FXML
    private TextField txt_contact_number;
    @FXML
    private TextField txt_services_filter;

    @FXML
    private TableView table_delivery;
    @FXML
    private TableView table_services;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //generateDeliveryID();
        txt_order_date.setText(LocalDate.now().toString());
        loadServicesTable();
        loadrowfromtable();
        //generateDeliveryID();


        table_delivery.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            DeliveryModel del = (DeliveryModel) obs.getValue();


            if (del == null) {
                return;
            }
        });

        /*table_services.getSelectionModel().selectedItemProperty().addListener((obs,oldvalue,newvalue) -> {
            ServiceModel ser = (ServiceModel) obs.getValue();

            if(ser == null){
                return;
            }
        });*/
        loadDeliveriesTable();
        //loadServicesTable();


        /*table_services.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            ServiceModel ser = (ServiceModel) obs.getValue();

            if (ser == null) {
                return;
            }
        });*/
    }

    public void loadDeliveriesTable() {
        TableColumn<DeliveryModel, String> delivery_id_col = new TableColumn<>("Delivery ID");
        delivery_id_col.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));

        TableColumn<DeliveryModel, String> order_date_col = new TableColumn<>("Order Date");
        order_date_col.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<DeliveryModel, String> delivery_cost_col = new TableColumn<>("Delivery cost");
        delivery_cost_col.setCellValueFactory(new PropertyValueFactory<>("deliveryCost"));

        TableColumn<DeliveryModel, String> quantity_col = new TableColumn<>("Quantity");
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("deliveryQty"));

        table_delivery.getColumns().clear();
        table_delivery.setItems(getAllDeliveries());
        table_delivery.getColumns().addAll(delivery_id_col, order_date_col, delivery_cost_col, quantity_col);
    }

    public void loadServicesTable() {
        TableColumn<ServiceModel, String> service_id_col = new TableColumn<>("Service ID");
        service_id_col.setCellValueFactory(new PropertyValueFactory<>("serviceId"));

        TableColumn<ServiceModel, String> name_col = new TableColumn<>("Name");
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ServiceModel, String> address_col = new TableColumn<>("Address");
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<ServiceModel, String> contact_number_col = new TableColumn<>("Contact number");
        contact_number_col.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        table_services.getColumns().clear();
        table_services.setItems(getAllServices());
        table_services.getColumns().addAll(service_id_col, name_col, address_col, contact_number_col);
    }


    public ObservableList<DeliveryModel> getAllDeliveries() {
        ObservableList<DeliveryModel> delivery = FXCollections.observableArrayList();

        JDBC j = new JDBC();
        try {
            ResultSet rset = j.getData("SELECT * FROM delivery");
            while (rset.next()) {
                String deliveryid = rset.getString(1);
                String orderdate = rset.getString(2);
                Float deliverycost = rset.getFloat(3);
                int quantity = rset.getInt(4);

                delivery.add(new DeliveryModel(deliveryid, orderdate, deliverycost, quantity));

            }
            return delivery;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return delivery;
    }

    public ObservableList<ServiceModel> getAllServices() {
        ObservableList<ServiceModel> services = FXCollections.observableArrayList();

        JDBC j = new JDBC();
        try {
            ResultSet rset = j.getData("SELECT * FROM services");
            while (rset.next()) {
                String serviceid = rset.getString(1);
                String name = rset.getString(2);
                String address = rset.getString(3);
                String contact_number = rset.getString(4);

                services.add(new ServiceModel(serviceid, name, address, contact_number));

            }
            return services;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return services;
    }
    public void generateDeliveryID () {
        String delivery_id = "";
        Calendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR) % 100;
        int delCount = new delivery().getDeliveryCount()+ 1;

        int curr_len = (year +"" + delCount).length();
        int padding_len = 5 - curr_len;

        delivery_id = delivery_id + year;
        for (int i = 0; i < padding_len; i++) {
            delivery_id = delivery_id + 1;
        }
        delivery_id = delivery_id + delCount;

        txt_delivery_id.setText(delivery_id);

    }



    public void savedelivery() {
        if(checkEmptyField()){


            String delivery_id = txt_delivery_id.getText();
            String order_date = txt_order_date.getText();
            float delivery_cost = Float.parseFloat(txt_delivery_cost.getText());
            int qnt = Integer.parseInt(txt_quantity.getText());

            delivery del = new delivery(delivery_id, order_date, delivery_cost, qnt);
            del.addNewdelivery(del);

        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful");
        alert.setHeaderText("delivery" + delivery.getdelivery_id() +" Successfully saved");
        alert.showAndWait();
        }*/
            loadDeliveriesTable();
            //clear();
        }
    }


    public void updateDelivery() {
        String delivery_id = txt_delivery_id.getText();
        String order_date = txt_order_date.getText();
        float delivery_cost = Float.parseFloat(txt_delivery_cost.getText());
        int quantity = Integer.parseInt(txt_quantity.getText());

        delivery del = new delivery(delivery_id, order_date, delivery_cost, quantity);
        del.updateNewDelivery(del);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Item Update Confirmation");
        alert.setContentText("Do you want to Update this Delivery Details ? ");
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("pooup");
        if(result.get() == ButtonType.OK){
            System.out.println("ok");
            JDBC j = new JDBC();
            try{
                del.setData("UPDATE delivery set delivery_id='"+delivery_id+"',order_date='"+order_date+"',delivery_cost='"+delivery_cost+"', quantity='"+quantity+"'");
            } catch (Exception e){
                e.printStackTrace();
            }
            ObservableList<DeliveryModel> list = getAllDeliveries();
            table_delivery.setItems(list);
        }
        else{

        }
    }

    public void deleteDelivery() {
        String delivery_id = txt_delivery_id.getText();

        Alert confDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confDialog.setTitle("Confirm action!");
        confDialog.setHeaderText("Are you sure you want to permanently delete employee " + delivery_id + " ?");
        Optional<ButtonType> result = confDialog.showAndWait();

        if (result.get() != ButtonType.OK) {
            return;
        }
        new delivery().deleteDelivery(delivery_id);
        //new customer().deleteCustomer(customer_id);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Delivery  " + delivery_id + "  successfully deleted!");
        alert.showAndWait();
        loadDeliveriesTable();

    }

    public void saveServices() {
        if(checkEmptyField()){


            String service_id = txt_service_id.getText();
            String name = txt_name.getText();
            String address = txt_address.getText();
            String contact_number = txt_contact_number.getText();

            services ser = new services(service_id, name, address, contact_number);
            ser.addNewservices(ser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("services" + services.getservice_id() +" Successfully saved");
            alert.showAndWait();
        }
        loadServicesTable();
        clear();


    }
    public void updateServices() {
        String service_id = txt_service_id.getText();
        String name = txt_name.getText();
        String address = txt_address.getText();
        String contact_number = txt_contact_number.getText();

        services ser = new services(service_id, name, address, contact_number);
        ser.updateNewServices(ser);

        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("services" + ser.getService_id() + "Successfully updated!");
        alert.showAndWait();
        loadServicesTable();*/

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Services Update Confirmation");
        alert.setContentText("Do you want to Update this Service Details ? ");
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("pooup");
        if(result.get() == ButtonType.OK){
            System.out.println("ok");
            JDBC j = new JDBC();
            try{
                ser.setData("UPDATE services set service_id='"+service_id+"',name='"+name+"',address='"+address+"', contact_number='"+contact_number+"'");
            } catch (Exception e){
                e.printStackTrace();
            }
            ObservableList<DeliveryModel> list = getAllDeliveries();
            table_delivery.setItems(list);
        }
        else{
        }
    }

    public void deleteServices() {
        String service_id = txt_service_id.getText();

        Alert confDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confDialog.setTitle("Confirm action!");
        confDialog.setHeaderText("Are you sure you want to permanently delete services" + service_id + "?");
        Optional<ButtonType> result = confDialog.showAndWait();

        if (result.get() != ButtonType.OK) {
            return;
        }
        new services().deleteServices(service_id);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Services" + service_id + "Successfully deleted!");
        alert.showAndWait();

    }

    private ObservableList<delivery> getAllData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static boolean isEmptyField(String delivery_id) {
        int strlen = delivery_id.length();
        if (strlen == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(delivery_id + "cannot be empty");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    }


    private boolean checkEmptyField() {
        FormValidate fv = new FormValidate();

        if (fv.isEmptyField(txt_delivery_id.getText(), "Delivery ID")) {
            return true;
        } else if (fv.isEmptyField(txt_order_date.getText(), "Order Date")) {
            return true;
        } else if (fv.isEmptyField(txt_delivery_cost.getText(), "Delivery Cost")) {
            return true;
        } else if (fv.isEmptyField(txt_quantity.getText(), "Quantity")) {
            return true;
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private boolean checkEmptyFields() {
        FormValidate fv = new FormValidate();

        if (fv.isEmptyField(txt_service_id.getText(), "Service ID")) {
            return true;
        } else if (fv.isEmptyField(txt_name.getText(), "Name")) {
            return true;
        } else if (fv.isEmptyField(txt_address.getText(), "Address")) {
            return true;
        } else if (fv.isEmptyField(txt_contact_number.getText(), "ContactNumber")) {
            return true;
        }
        return false;
    }
    public void loadrowfromtable() {
        table_delivery.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            DeliveryModel d = (DeliveryModel) obs.getValue();
            if (d == null) {
                return;
            }
            String delivery_id = d.getDeliveryId();
            String order_date = d.getOrderDate();
            float delivery_cost = d.getDeliveryCost();
            int quantity = d.getDeliveryQty();

            txt_delivery_id.setText(delivery_id);
            txt_order_date.setText(order_date);
            txt_delivery_cost.setText(Float.toString(delivery_cost));
            txt_quantity.setText(Integer.toString(quantity));


        });
    }


}

