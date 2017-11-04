package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import models.Exchange;
import models.Invoice;
import util.FormValidate;
import util.JDBC;

public class SaleController implements Initializable {

    @FXML
    private TableView table_new_invoice;
    @FXML
    private TextField item_txt;
    @FXML
    private TextField desc_txt;
    @FXML
    private TextField qty_txt;
    @FXML
    private TextField unpr_txt;
    @FXML
    private DatePicker date_txt;
    @FXML
    private TextField inv_txt;
    @FXML
    private TextField disc_txt;
    @FXML
    private TextField cust_txt;
    @FXML
    private TextField gro_tot_txt;
    @FXML
    private TextField sub_disc_txt;
    @FXML
    private TextField net_tot_txt;
    @FXML
    private TextField cash_txt;
    @FXML
    private TextField bal_txt;
    @FXML
    private Label lbl_customer;
    @FXML
    private Button sale_add_btn;
    @FXML
    private Button cal_tot_but;

    //exchange section
    @FXML
    private TextField exch_id_txt;
    @FXML
    private DatePicker ret_date_txt;
    @FXML
    private TextField inv_no_txt;
    @FXML
    private TextField ret_itmno_txt;
    @FXML
    private TextField ret_qty_txt;
    @FXML
    private TableView table_exchange_info;
    @FXML
    private TextField ret_itm_txt;
    @FXML
    private TextField cmt_txt;
    @FXML
    private TextField ret_cost_txt;
    @FXML
    private TextField tot_qty_txt;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_cal;
    @FXML
    private TextField sub_ret_txt;

    //history section
    @FXML
    private TableView table_sales_history;
    @FXML
    private DatePicker date_from;
    @FXML
    private DatePicker date_to;
    // @FXML
    // private Checkbox check_today;
    @FXML
    private TextField tot_sale_txt;

    ObservableList<Invoice> invoices = FXCollections.observableArrayList();
    ObservableList<Exchange> exchange = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTable();
       
        loadExchange_table();
        loadSaleshistory();
        loadrowfromTable();
        loadrowSalesTable();

        date_txt.setValue(LocalDate.now());
        date_txt.setDisable(true);
        disc_txt.setText("0.0");
        ret_date_txt.setValue(LocalDate.now());
        ret_date_txt.setDisable(true);
        inv_txt.setText(new Invoice().generateInvoiceNo());
        inv_txt.setDisable(true);
        exch_id_txt.setText(new Exchange().generateExchangeId());
        exch_id_txt.setDisable(true);
        ret_itm_txt.setDisable(true);
        tot_qty_txt.setDisable(true);
        ret_cost_txt.setDisable(true);
        sub_ret_txt.setDisable(true);
        tot_sale_txt.setDisable(true);
    }

    public ObservableList<Invoice> addItem() {
        String invoice_no = inv_txt.getText();
        String item_no = item_txt.getText();
        String description = desc_txt.getText();
        int qty = Integer.parseInt(qty_txt.getText());
        double unitPrice = Double.parseDouble(unpr_txt.getText());
        double amount = qty * unitPrice;
        double discount = Double.parseDouble(disc_txt.getText());
        double tot_discount = qty * discount;
        Date date = Date.valueOf(date_txt.getValue());
        double net_amount = amount - tot_discount;

        invoices.add(new Invoice(invoice_no, item_no, description, qty, unitPrice, amount, tot_discount, net_amount, date));
        return invoices;
    }


    public ObservableList<Exchange> saveRec() {
        String exchange_id = exch_id_txt.getText();
        String ret_date = ret_date_txt.getValue().toString();
        String inv_no = inv_no_txt.getText();
        String item_no = ret_itmno_txt.getText();
        String description = ret_itm_txt.getText();
        int qty = Integer.parseInt(ret_qty_txt.getText());
        String comm = cmt_txt.getText();
        double ret_cost = Double.parseDouble(ret_cost_txt.getText());
        exchange.add(new Exchange(exchange_id, ret_date, inv_no, item_no, description, qty, comm, ret_cost));
        return exchange;
    }

    public ObservableList<Invoice> getsalesRecords() {
        boolean filtered = false;
        Date start = null;
        Date end = null;
        if (date_from.getValue() != null && date_to.getValue() != null) {
            start = Date.valueOf(date_from.getValue());
            end = Date.valueOf(date_to.getValue());
            filtered = true;
        }

        JDBC j = new JDBC();
        ObservableList<Invoice> records = FXCollections.observableArrayList();
        try {
            ResultSet rset = null;
            if (!filtered) {
                rset = j.getData("SELECT * FROM waterford_mobile.sales order by date desc");
            } else {
                rset = j.getData("select * from waterford_mobile.sales where date between '" + start + "' and '" + end + "' order by date desc");
            }
            while (rset.next()) {
                Date date = rset.getDate(1);
                String inv_no = rset.getString(2);
                String emp_id = rset.getString(3);
                String cust_id = rset.getString(4);
                String item_no = rset.getString(5);
                String desc = rset.getString(6);
                int qty = rset.getInt(7);
                double unit_price = rset.getDouble(8);
                double amnt = rset.getDouble(9);
                double tot_disc = rset.getDouble(10);
                double net_amnt = rset.getDouble(11);

                records.add(new Invoice(date, inv_no, emp_id, cust_id, item_no, desc, qty, unit_price, amnt, tot_disc, net_amnt));
            }
            return records;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return records;
    }

    public void loadrowfromTable() {
        table_exchange_info.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Exchange e = (Exchange) obs.getValue();

            if (e == null) {
                return;
            }
            String inv_no = e.getInvoice_no();
            String item_no = e.getItem_no();
            int ret_qty = e.getRet_qty();
            double ret_cost = e.getReturnCost();
            String cmnt = e.getComments();

            inv_no_txt.setText(inv_no);
            ret_itmno_txt.setText(item_no);
            searchInvoice();
            ret_qty_txt.setText(Integer.toString(ret_qty));
            ret_cost_txt.setText(Double.toString(ret_cost));
            cmt_txt.setText(cmnt);

        });

    }

     public void loadrowSalesTable() {
        table_new_invoice.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Invoice i = (Invoice) obs.getValue();

            if (i == null) {
                return;
            }
            String item_no = i.getItem_no();
            String desc = i.getDescription();
            double unit_price = i.getUnitPrice();
            double tot_discount = i.getTot_discount();
            int qty = i.getQty();
            double discount=tot_discount/qty;
           
            
            item_txt.setText(item_no);
            desc_txt.setText(desc);
            unpr_txt.setText(Double.toString(unit_price));
            disc_txt.setText(Double.toString(discount));
            qty_txt.setText(Integer.toString(qty));
            
        });

    }
    

    public void loadTable() {
        if(SalesValidation()){
            return;
        }

        if (!validateDiscount()) {
            return;
        }
        if (!validateQty()) {
            return;
        }
        if (!getStockQty()) {
            return;
        }

        table_new_invoice.setItems(addItem());
        clearAddItemFields();
        item_txt.requestFocus();
    }



    public void loadexchange() {

        table_exchange_info.setItems(saveRec());
        calculateExchTot();
        clearExchangeItemFields();

    }

    public boolean validateDiscount() {
        double unit_price = Double.parseDouble(unpr_txt.getText());
        double discount = Double.parseDouble(disc_txt.getText());

        if (discount >= unit_price) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Discount should be less than item price.");
            alert.showAndWait();
            return false;
        } else if (discount < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Discount cannot be negative.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public boolean validateQty() {
        int qty = Integer.parseInt(qty_txt.getText());
        if (qty <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Quantity should be positive!!!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public boolean getStockQty() {
        JDBC j = new JDBC();
        String item_code = item_txt.getText();
        int qty = Integer.parseInt(qty_txt.getText());

        try {
            ResultSet rset = j.getData("SELECT qtyOnHand FROM waterford_mobile.stock WHERE pr_code = '" + item_code + "' ");

            if (rset.next()) {
                int qty_on_hand = Integer.parseInt(rset.getString(1));
                if (qty_on_hand < qty) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Stock quantity is not enough.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean ValidatereturnQty() {
        if (ret_qty_txt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Return quantity cannot Empty");
            alert.showAndWait();
            return false;
        }

        int ret_qty = Integer.parseInt(ret_qty_txt.getText());
        int qty = Integer.parseInt(tot_qty_txt.getText());

        if (ret_qty > qty || ret_qty == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Return quantity is Invalid.");
            alert.showAndWait();
            return false;
        } else if (ret_qty < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Return quantity cannot be negative.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }

    }


    private void initTable() {
        TableColumn<Invoice, String> item_no_col = new TableColumn<>("Item No");
        item_no_col.setCellValueFactory(new PropertyValueFactory<>("item_no"));

        TableColumn<Invoice, String> description_col = new TableColumn<>("Description");
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Invoice, String> qty_col = new TableColumn<>("Qty");
        qty_col.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<Invoice, String> unitprice_col = new TableColumn<>("Unit Price");
        unitprice_col.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        TableColumn<Invoice, String> amount_col = new TableColumn<>("Amount");
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Invoice, String> tot_disc_col = new TableColumn<>("Total Discount");
        tot_disc_col.setCellValueFactory(new PropertyValueFactory<>("tot_discount"));

        TableColumn<Invoice, String> ret_amount_col = new TableColumn<>("Net Amount");
        ret_amount_col.setCellValueFactory(new PropertyValueFactory<>("net_amount"));

        table_new_invoice.getColumns().addAll(item_no_col, description_col, qty_col, unitprice_col, amount_col, tot_disc_col, ret_amount_col);
    }


    public void loadSaleshistory() {
        TableColumn<Invoice, java.sql.Date> date_col = new TableColumn<>("Date");
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Invoice, String> inv_col = new TableColumn<>("Invoice No");
        inv_col.setCellValueFactory(new PropertyValueFactory<>("invoice_no"));

        TableColumn<Invoice, String> emp_id_col = new TableColumn<>("Emp Id");
        emp_id_col.setCellValueFactory(new PropertyValueFactory<>("employee"));

        TableColumn<Invoice, String> cust_id_col = new TableColumn<>("Customer");
        cust_id_col.setCellValueFactory(new PropertyValueFactory<>("customer"));

        TableColumn<Invoice, String> item_no_col = new TableColumn<>("Item No");
        item_no_col.setCellValueFactory(new PropertyValueFactory<>("item_no"));

        TableColumn<Invoice, String> disc_col = new TableColumn<>("Discription");
        disc_col.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Invoice, String> qty_col = new TableColumn<>("Qty");
        qty_col.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<Invoice, String> unit_pr_col = new TableColumn<>("Unit Price");
        unit_pr_col.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        TableColumn<Invoice, String> amnt_col = new TableColumn<>("Amount");
        amnt_col.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Invoice, String> tot_disc_col = new TableColumn<>("Total Discount");
        tot_disc_col.setCellValueFactory(new PropertyValueFactory<>("tot_discount"));

        TableColumn<Invoice, String> net_amnt_col = new TableColumn<>("Net Amount");
        net_amnt_col.setCellValueFactory(new PropertyValueFactory<>("net_amount"));

        ObservableList<Invoice> invoices = getsalesRecords();
        table_sales_history.getItems().clear();
        table_sales_history.getColumns().clear();

        table_sales_history.setItems(invoices);
        table_sales_history.getColumns().addAll(date_col, inv_col,
                emp_id_col, cust_id_col, item_no_col, disc_col, qty_col,
                unit_pr_col, amnt_col, tot_disc_col, net_amnt_col);
        calculateHistoryTot();

    }

    private void loadExchange_table() {
        TableColumn<Exchange, String> item_no_col = new TableColumn<>("Item No");
        item_no_col.setCellValueFactory(new PropertyValueFactory<>("item_no"));

        TableColumn<Exchange, String> item_desc_col = new TableColumn<>("Description");
        item_desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Exchange, String> ret_qty_col = new TableColumn<>("Return Quentity");
        ret_qty_col.setCellValueFactory(new PropertyValueFactory<>("ret_qty"));

        TableColumn<Exchange, String> item_cost_col = new TableColumn<>("Return Cost");
        item_cost_col.setCellValueFactory(new PropertyValueFactory<>("returnCost"));

        TableColumn<Exchange, String> comnt_col = new TableColumn<>("Comment");
        comnt_col.setCellValueFactory(new PropertyValueFactory<>("comments"));

        table_exchange_info.getColumns().addAll(item_no_col, item_desc_col, ret_qty_col, item_cost_col, comnt_col);

    }



    public void saveinvoice() {
        String invoice_no = inv_txt.getText();
        String date = date_txt.getValue().toString();
        double gross_tot = Double.parseDouble(gro_tot_txt.getText());
        double sub_disc = Double.parseDouble(sub_disc_txt.getText());
        double net_tot = Double.parseDouble(net_tot_txt.getText());
        double cash = Double.parseDouble(cash_txt.getText());
        double balance = Double.parseDouble(bal_txt.getText());

        JDBC j = new JDBC();
        j.setData("INSERT INTO invoice(inv_no, date, gross_tot, sub_disc, net_tot, cash, balance) "
                + "VALUES('" + invoice_no + "', '" + date + "', '" + gross_tot + "', '" + sub_disc + "', '" + net_tot + "', '" + cash + "', '" + balance + "')");

    }


    public void checkout() {
        clearAddItemFields();
        if(EmptyfieldCheck()){
            return;
        }
        saveInvoiceItems();
        saveinvoice();
        initNewInvoice();
        loadSaleshistory();
    }


    private void initNewInvoice() {
        Invoice i = new Invoice();
        inv_txt.setText(i.generateInvoiceNo());
        gro_tot_txt.clear();
        sub_disc_txt.clear();
        net_tot_txt.clear();
        cash_txt.clear();
        bal_txt.clear();

        table_new_invoice.getItems().clear();
    }


    public void saveExchange() {
        saveExchangeItem();
        initNewExchange();
    }



    public void searchItem() {
        JDBC j = new JDBC();
        String item_code = item_txt.getText();
        try {
            ResultSet rset = j.getData("SELECT pr_code,description,unitPrice FROM waterford_mobile.stock WHERE pr_code = '" + item_code + "' ");

            if (rset.next()) {
                item_code = rset.getString(1);
                String des = rset.getString(2);
                String up = rset.getString(3);

                item_txt.setText(item_code);
                desc_txt.setText(des);
                unpr_txt.setText(up);
                disc_txt.requestFocus();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void searchInvoice() {
        JDBC j = new JDBC();
        String invoice_no = inv_no_txt.getText();
        String item_no = ret_itmno_txt.getText();

        try {
            ResultSet rset = j.getData("Select date,inv_no,employee_id,customerId,item_no,description,qty,unit_price,amount,tot_discount,net_amt From sales WHERE inv_no = '" + invoice_no + "' and item_no = '" + item_no + "' ");
            if (!rset.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invoice details are wrong.");
                alert.showAndWait();
            } else {

                String des = rset.getString("description");
                String Qty = rset.getString("qty");

                ret_itm_txt.setText(des);
                tot_qty_txt.setText(Qty);
            }
        } catch (Exception ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void calcReturnCost() {
        if (!ValidatereturnQty()) {
            return;
        }
        JDBC j = new JDBC();
        String invoice_no = inv_no_txt.getText();
        double cost = 0;
        double ret_cost = 0;
        try {
            ResultSet rset = j.getData("Select* From sales WHERE inv_no = '" + invoice_no + "' ");
            if (rset.next()) {

                String Qty = rset.getString("qty");
                String tot = rset.getString("net_amt");

                cost = Double.parseDouble(tot) / Integer.parseInt(Qty);
                int ret_qty = Integer.parseInt(ret_qty_txt.getText());
                ret_cost = cost * ret_qty;
                ret_cost_txt.setText(Double.toString(ret_cost));
            }
        } catch (Exception ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearAddItemFields() {
        item_txt.clear();
        desc_txt.clear();
        unpr_txt.clear();
        disc_txt.clear();
        qty_txt.clear();
    }

    private void clearExchangeItemFields() {
        inv_no_txt.clear();
        ret_itmno_txt.clear();
        ret_itm_txt.clear();
        tot_qty_txt.clear();
        ret_qty_txt.clear();
        ret_cost_txt.clear();
        cmt_txt.clear();

    }


    public void calculateTotal() {
        clearAddItemFields();

        double gross_total = 0;
        double sub_discount = 0;
        double net_total = 0;

        ObservableList<Invoice> invoices = table_new_invoice.getItems();

        for (Invoice inv : invoices) {
            gross_total += inv.getAmount();
            sub_discount += inv.getTot_discount();
        }

        net_total = gross_total - sub_discount;

        gro_tot_txt.setText(Double.toString(gross_total));
        sub_disc_txt.setText(Double.toString(sub_discount));
        net_tot_txt.setText(Double.toString(net_total));
    }


    public void calculateHistoryTot(){
        double sale_total = 0;

        ObservableList<Invoice> invoices = table_sales_history.getItems();

        for (Invoice inv : invoices) {
            sale_total += inv.getNet_amount();
          
        }
        tot_sale_txt.setText(Double.toString(sale_total));
    }


    public void calculateExchTot() {
        double sub_return = 0;

        ObservableList<Exchange> exchange = table_exchange_info.getItems();

        for (Exchange exc : exchange) {
            sub_return += exc.getReturnCost();
        }

        sub_ret_txt.setText(Double.toString(sub_return));
    }

    
    public boolean EmptyfieldCheck(){
        String cash = cash_txt.getText();
        FormValidate v = new FormValidate();

         if (v.isEmptyField(cash, "cash")) {
            return true;
        }
        return false;
    }


    public boolean SalesValidation(){
    String item_no = item_txt.getText();
    String description = desc_txt.getText();
    String unit_pr = unpr_txt.getText();
    String qty = qty_txt.getText();
     FormValidate v = new FormValidate();

         if (v.isEmptyField(item_no, "item_no") || v.isEmptyField(description, "description") || v.isEmptyField(unit_pr,"unitPrice") || v.isEmptyField(qty, "qty")) {
            return true;
        }
        return false;
    }
    
    
    public void calc_balance() {
       
       if (EmptyfieldCheck()) {
            return;
        }
        double net_total = Double.parseDouble(net_tot_txt.getText());
        double cash = Double.parseDouble(cash_txt.getText());
        double balance = 0;
       
        
        if (cash < net_total) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Payment unacceptable");
            alert.showAndWait();
            return;
        }

        balance = cash - net_total;

        bal_txt.setText(Double.toString(balance));
    }

    public void saveInvoiceItems() {
        ObservableList<Invoice> invoices = table_new_invoice.getItems();

        java.sql.Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String invoice_no = "";
        String employee_id = "";
        String customer_id = "";
        String item_code = "";
        String description = "";
        int qty = 0;
        double unit_price = 0;
        double amount = 0;
        double tot_disc = 0;
        double net_amount = 0;

        for (Invoice inv : invoices) {
            date = inv.getDate();
            invoice_no = inv_txt.getText();
            employee_id = inv.getEmployee();
            customer_id = inv.getCustomer();
            item_code = inv.getItem_no();
            description = inv.getDescription();
            qty = inv.getQty();
            unit_price = inv.getUnitPrice();
            amount = inv.getAmount();
            tot_disc = inv.getTot_discount();
            net_amount = inv.getNet_amount();

            System.out.println("I AM CALLING");

            JDBC j = new JDBC();
            j.setData("INSERT INTO sales(date,inv_no,employee_id,customerId,item_no,description,qty,unit_price,amount,tot_discount,net_amt)"
                    + "VALUES('" + date + "','" + invoice_no + "','" + " " + "','" + " " + "','" + item_code + "','" + description + "','" + qty + "','" + unit_price + "','" + amount + "','" + tot_disc + "','" + net_amount + "')");

        }
    }

    public void saveExchangeItem() {
        ObservableList<Exchange> exchange = table_exchange_info.getItems();

        String exch_id = "";
        String date = "";
        String inv_no = "";
        String item_no = "";
        int ret_qty = 0;
        String description = "";
        String comnt = "";
        double ret_cost = 0;
        String employee_id = "";
        String customer_id = "";

        for (Exchange exc : exchange) {
            date = exc.getDate();
            exch_id = exch_id_txt.getText();
            inv_no = exc.getInvoice_no();
            item_no = exc.getItem_no();
            ret_qty = exc.getRet_qty();
            description = exc.getDescription();
            comnt = exc.getComments();
            ret_cost = exc.getReturnCost();
            employee_id = exc.getEmployee();
            customer_id = exc.getCustomer();

            System.out.println("I AM CALLING");

            JDBC j = new JDBC();
            j.setData("INSERT INTO exchange(exchange_id,date,invoice_no,item_no,description,ret_qty,comment,ret_cost,emp_id,cust_id)"
                    + "VALUES('" + exch_id + "','" + date + "','" + inv_no + "','" + item_no + "','" + description + "','" + ret_qty + "','" + comnt + "','" + ret_cost + "','" + " " + "','" + " " + "')");
        }
    }

    private void initNewExchange() {
        Exchange e = new Exchange();
        exch_id_txt.setText(e.generateExchangeId());
        table_exchange_info.getItems().clear();
    }

    public void updateExchange() {
        ObservableList<Exchange> items = table_exchange_info.getItems();
        final ObservableList<Exchange> selected_items = table_exchange_info.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Item Update Confirmation");
        alert.setContentText("Do you want to Update this Item Details ? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Exchange e = new Exchange();
            e.setExchange_id(exch_id_txt.getText());
            e.setDate(ret_date_txt.getValue().toString());
            e.setInvoice_no(inv_no_txt.getText());
            e.setItem_no(ret_itmno_txt.getText());
            e.setDescription(ret_itm_txt.getText());
            e.setRet_qty(Integer.parseInt(ret_qty_txt.getText()));
            e.setComments(cmt_txt.getText());
            e.setReturnCost(Double.parseDouble(ret_cost_txt.getText()));

            table_exchange_info.getItems().add(e);
            selected_items.forEach(items::remove);
            calculateExchTot();
            clearExchangeItemFields();
        }
    }
    

    public void DeleteExchange(){
         ObservableList<Exchange> items = table_exchange_info.getItems();
        final ObservableList<Exchange> selected_items = table_exchange_info.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Item Update Confirmation");
        alert.setContentText("Do you want to Delete this Item detail? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            selected_items.forEach(items::remove);
            calculateExchTot();
            clearExchangeItemFields();
        }
    }

    public void updateSalesItem(){
        ObservableList<Invoice> items = table_new_invoice.getItems();
        final ObservableList<Invoice> selected_items = table_new_invoice.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Item Update Confirmation");
        alert.setContentText("Do you want to Update this Item Details ? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Invoice i = new Invoice();
         
            i.setInvoice_no(inv_txt.getText());
            i.setItem_no(item_txt.getText());
            i.setDescription(desc_txt.getText());
            i.setQty(Integer.parseInt(qty_txt.getText()));
            i.setUnitPrice(Double.parseDouble(unpr_txt.getText()));
            int qty = Integer.parseInt(qty_txt.getText());
            double unitPrice = Double.parseDouble(unpr_txt.getText());
            double amount = qty * unitPrice;
            i.setAmount(amount);
            double discount = Double.parseDouble(disc_txt.getText());
            double tot_discount = qty * discount;
            i.setTot_discount(tot_discount);
            double net_amount = amount - tot_discount;
            i.setNet_amount(net_amount);
            i.setDate(Date.valueOf(date_txt.getValue()));
            table_new_invoice.getItems().add(i);
            
            selected_items.forEach(items::remove);
            clearAddItemFields();
        }
        
    }
    public void DeleteSalesItem(){
        ObservableList<Invoice> items = table_new_invoice.getItems();
        final ObservableList<Invoice> selected_items = table_new_invoice.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Item Update Confirmation");
        alert.setContentText("Do you want to Delete this Item detail? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            selected_items.forEach(items::remove);
            clearAddItemFields();
        }        
        
    }
}

    
