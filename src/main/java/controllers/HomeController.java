package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button sidebar_business_overview;
    @FXML
    private Button sidebar_human_resources;
    @FXML
    private Button sidebar_sales;
    @FXML
    private Button sidebar_inventory;
    @FXML
    private Button sidebar_suppliers;
    @FXML
    private Button sidebar_finance;
    @FXML
    private Button sidebar_customers;
    @FXML
    private Button sidebar_deliveries;
    @FXML
    private Button sidebar_advertising;
    @FXML
    private Button sidebar_infrastructure;
    @FXML
    private Button sidebar_useraccounts;
    @FXML
    private BorderPane border_pane_home;
    @FXML
    private StackPane stackpane_home_center;
    @FXML
    private Label lbl_logged_in_user;

    /**
     * load Supplier FXML into center of the home borderpane.
     */
    public void loadSupplierPanel() {
        loadCenterPanel("/views/Supplier.fxml");
    }
    public void loadFinancePanel() {
        loadCenterPanel("/views/Finance.fxml");
    }
    public void loadOffersPanel() {
        loadCenterPanel("/views/Advertising.fxml");
    }
    public void loadInventoryPanel() {
        loadCenterPanel("/views/Inventory.fxml");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadHumanResourcespanel();
        lbl_logged_in_user.setText(LoginController.loggedInUser);
    }
    /**
     * load the Human Resources FXML into center of the home borderpane.
     */
    public void loadHumanResourcespanel() {
        loadCenterPanel("/views/HumanResources.fxml");
    }
    /**
     * load Deliveries FXML into center of the home borderpane.
     */
    public void loadDeliveryPanel() {
        loadCenterPanel("/views/DeliveryandDeliveryServices.fxml");
    }

    /**
     * Loads the FXML scene to the center of home borderpane.
     *
     * @param fxml_path
     */
    public void loadCenterPanel(String fxml_path) {
        stackpane_home_center.getChildren().clear();
        try {
            stackpane_home_center.getChildren().clear();
            stackpane_home_center.getChildren().add(FXMLLoader.load(getClass().getResource(fxml_path)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadCustomerPanel() {
        loadCenterPanel("/views/Customer_Details.fxml");
    }

    public void loadSalesPanel() {
        loadCenterPanel("/views/Sale.fxml");
    }

}
