/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CashPayment;
import models.ChequePayments;
import models.DailyExpenses;
import models.ElectricityBill;
import models.Profit;
import models.RentalPayment;
import models.WaterBill;
import models.searchCashPayee;
import models.searchChequePayee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JDBC;

/**
 * FXML Controller class
 *
 * @author DinuX
 */
public class FinanceController implements Initializable {

    JDBC db = new JDBC();
    static final Logger LOG = LoggerFactory.getLogger(PasswordRecoveryController.class);
    //electricitybill add fxid
    @FXML
    private TextField txt_ebill_no;
    @FXML
    private TextField txt_eamount;
    @FXML
    private TextField txt_eunits;
    @FXML
    private DatePicker txt_edatep;

    //waterbill add fxid
    @FXML
    private TextField txt_wbill_no;
    @FXML
    private TextField txt_wamount;
    @FXML
    private TextField txt_wunits;
    @FXML
    private DatePicker txt_wdatep;
    //rentals add fxid
    @FXML
    private TextField txt_rid;
    @FXML
    private TextField txt_ramount;
    @FXML
    private DatePicker txt_rdatep;
    //daily expenses add fxid
    @FXML
    private TextField txt_did;
    @FXML
    private DatePicker txt_ddate;
    @FXML
    private TextField txt_damount;
    @FXML
    private TextArea txt_ddescription;
    @FXML
    private Label lbl_fdate;
    @FXML
    private Label txt_tdate;
    @FXML
    private ChoiceBox<?> choice_category;
    //add cash payments fxid
    @FXML
    private TextField txt_cash_id;
    @FXML
    private TextField txt_cash_amount;
    @FXML
    private DatePicker txt_cash_date;
    @FXML
    private TextField txt_cash_payee;
    //add cheque payments fxid
    @FXML
    private TextField txt_cheque_id;
    @FXML
    private TextField txt_cheque_amount;
    @FXML
    private TextField txt_cheque_payee;
    @FXML
    private DatePicker txt_issue;
    @FXML
    private DatePicker txt_valid;
    @FXML
    private TableView table_electricity_bills;
    @FXML
    private TableView table_water_bills;
    @FXML
    private TableView table_rentals;
    @FXML
    private TableView table_expenses;
    @FXML
    private TableView table_cash_payment;
    @FXML
    private TableView table_cheque_payments;
    @FXML
    private Label lbl_ebill;

    @FXML
    private Label lbl_emount;

    @FXML
    private Label lbl_eunits;

    @FXML
    private Label lbl_edate;
    @FXML
    private Label lbl_wbill;

    @FXML
    private Label lbl_wamount;

    @FXML
    private Label lbl_wunits;

    @FXML
    private Label lbl_wdate;
    @FXML
    private Button btn_addelecbill;
    @FXML
    private Button btn_addwaterbill;
    @FXML
    private Button btn_addrental;
    @FXML
    private Button btn_addexpenses;
    @FXML
    private Button btn_addcashpay;
    @FXML
    private Button btn_addchequepay;

    @FXML
    private Button btn_updateelecbill;

    @FXML
    private Label lbl_ramount;
    @FXML
    private Label lbl_rdate;
    @FXML
    private Label lbl_ddate;
    @FXML
    private Label lbl_damount;
    @FXML
    private Label lbl_ddescription;

    @FXML
    private Label lbl_cshamount;
    @FXML
    private Label lbl_cshdate;
    @FXML
    private Label lbl_cshpayee;

    @FXML
    private Label lbl_chqno;
    @FXML
    private Label lbl_chqamount;
    @FXML
    private Label lbl_chqpayee;
    @FXML
    private Label lbl_chqissued;
    @FXML
    private Label lbl_validd;
    @FXML
    private DatePicker dtp_efrom;
    @FXML
    private DatePicker dtp_eto;
    @FXML
    private DatePicker dtp_wfrom;
    @FXML
    private DatePicker dtp_wto;
    @FXML
    private DatePicker dtp_rfrom;
    @FXML
    private DatePicker dtp_rto;
    @FXML
    private DatePicker dtp_ddate;

    //profit
    @FXML
    private DatePicker dtp_pfrom;
    @FXML
    private DatePicker dtp_pto;
    @FXML
    private TextField txt_pincome;
    @FXML
    private TextField txt_pexpenses;
    @FXML
    private TextField txt_pprofit;

    @FXML
    private ChoiceBox cmb_cpayee;
    @FXML
    private ChoiceBox cmb_chpayee;
    @FXML
    private TableView tbl_profit;
    @FXML
    private Accordion acc_finance;
    @FXML
    private TitledPane pane_utility;
    @FXML
    private TitledPane pane_profit;
    @FXML
    private TitledPane pane_payments;

    static int rental_id = 0;
    static int expenses_id = 0;
    static int cash_id = 0;

    /**
     * Initializes the controller class.
     */
    
    String currentEbill="";
    String waterWbill="";
    String chequeDetails="";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadElecBillTable();
        loadWaterBillTable();
        loadRentalsTable();
        loadDailyExpensesTable();
        loadCashPaymentsTable();
        loadChequePaymentsTable();
        loadProfitTable();
//        acc_utility.setExpandedPane(pane_ebill);
        acc_finance.setExpandedPane(pane_utility);
        
        

        lbl_emount.setVisible(false);
        lbl_edate.setVisible(false);
        lbl_eunits.setVisible(false);
        lbl_ebill.setVisible(false);

        lbl_wbill.setVisible(false);
        lbl_wamount.setVisible(false);
        lbl_wunits.setVisible(false);
        lbl_wdate.setVisible(false);

        lbl_ramount.setVisible(false);
        lbl_rdate.setVisible(false);

        lbl_ddate.setVisible(false);
        lbl_damount.setVisible(false);
        lbl_ddescription.setVisible(false);

        lbl_cshamount.setVisible(false);
        lbl_cshdate.setVisible(false);
        lbl_cshpayee.setVisible(false);

        lbl_chqno.setVisible(false);
        lbl_chqamount.setVisible(false);
        lbl_chqpayee.setVisible(false);
        lbl_chqissued.setVisible(false);
        lbl_validd.setVisible(false);
        
        

        ObservableList<searchCashPayee> list11 = getSearchCashPaymentData();
        for (searchCashPayee c : list11) {
            cmb_cpayee.getItems().add(c.getChpayee());
            cmb_cpayee.getSelectionModel().selectFirst();
        }
        ObservableList<searchChequePayee> list12 = getSearchChequePaymentData();
        for (searchChequePayee c : list12) {
            cmb_chpayee.getItems().add(c.getChqpayee());
            cmb_chpayee.getSelectionModel().selectFirst();
        }

        table_electricity_bills.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (obs.getValue() == null) {
                return;
            }
            ElectricityBill ebill = (ElectricityBill) obs.getValue();
            currentEbill = ebill.getEbill_no();
            txt_ebill_no.setText(ebill.getEbill_no());
            txt_eamount.setText(Double.toString(ebill.getEamount()));
            txt_eunits.setText(Integer.toString(ebill.getEunits()));
            txt_edatep.setValue(LocalDate.parse(ebill.getPayment_date()));
            btn_addelecbill.setVisible(false);
            LOG.info("Electricity_bill added");
        });
        table_water_bills.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (obs.getValue() == null) {
                return;
            }
            WaterBill wbill = (WaterBill) obs.getValue();
            waterWbill=wbill.getWbill_no();
            txt_wbill_no.setText(wbill.getWbill_no());
            txt_wamount.setText(Double.toString(wbill.getWamount()));
            txt_wunits.setText(Integer.toString(wbill.getWunits()));
            txt_wdatep.setValue(LocalDate.parse(wbill.getPayment_date()));
            btn_addwaterbill.setVisible(false);
            LOG.info(" WaterBill added");
        });
        table_rentals.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (obs.getValue() == null) {
                return;
            }
            RentalPayment rentals = (RentalPayment) obs.getValue();

            if (rentals != null) {
                rental_id = (rentals.getRid());
                txt_ramount.setText(Double.toString(rentals.getRamount()));
                txt_rdatep.setValue(LocalDate.parse(rentals.getRpayment_date()));
                btn_addrental.setVisible(false);
            }

        });
        table_expenses.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (obs.getValue() == null) {
                return;
            }
            DailyExpenses dbill = (DailyExpenses) obs.getValue();

            expenses_id = (dbill.getId());
            txt_damount.setText(Double.toString(dbill.getAmount()));
            txt_ddescription.setText(dbill.getDescription());
            txt_ddate.setValue(LocalDate.parse(dbill.getDate()));
            btn_addexpenses.setVisible(false);

        });
        table_cash_payment.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (obs.getValue() == null) {
                return;
            }

            CashPayment cashpay = (CashPayment) obs.getValue();
            cash_id = cashpay.getChid();
            txt_cash_id.setText(Integer.toString(cashpay.getChid()));

            txt_cash_amount.setText(Double.toString(cashpay.getChamount()));

            txt_cash_date.setValue(LocalDate.parse(cashpay.getChdate()));
            txt_cash_payee.setText(cashpay.getChpayee());
            btn_addcashpay.setVisible(false);
        });
        table_cheque_payments.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (obs.getValue() == null) {
                return;
            }
            ChequePayments chequepay = (ChequePayments) obs.getValue();

            if (chequepay == null) {
                return;
            }
            chequeDetails=chequepay.getChqno();
            
            txt_cheque_id.setText(chequepay.getChqno());
            txt_cheque_amount.setText(Double.toString(chequepay.getChqamout()));
            txt_cheque_payee.setText(chequepay.getChqpayee());
            txt_issue.setValue(LocalDate.parse(chequepay.getChqissuedate()));
            txt_valid.setValue(LocalDate.parse(chequepay.getChqvaliddate()));
            btn_addchequepay.setVisible(false);
        });
    }

    public ObservableList<ElectricityBill> getAllElectricityBillData() {
        JDBC j = new JDBC();
        ObservableList<ElectricityBill> electricitybills = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM electricity_bill");
            while (rset.next()) {
                String bill_no = rset.getString(1);
                Double amount = rset.getDouble(2);
                int units = rset.getInt(3);
                String payment_date = rset.getString(4);

                electricitybills.add(new ElectricityBill(bill_no, amount, units, payment_date));
            }
            return electricitybills;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return electricitybills;

    }

    public ObservableList<WaterBill> getAllWaterBillData() {
        JDBC j = new JDBC();
        ObservableList<WaterBill> waterbills = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM water_bill");
            while (rset.next()) {
                String bill_no = rset.getString(1);
                Double amount = rset.getDouble(2);
                int units = rset.getInt(3);
                String payment_date = rset.getString(4);

                waterbills.add(new WaterBill(bill_no, amount, units, payment_date));
            }
            return waterbills;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return waterbills;
    }

    public ObservableList<RentalPayment> getAllRentalsData() {
        JDBC j = new JDBC();
        ObservableList<RentalPayment> rentals = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM rentals");
            while (rset.next()) {
                Integer rental_id = rset.getInt(1);
                Double amount = rset.getDouble(2);
                String payment_date = rset.getString(3);

                rentals.add(new RentalPayment(rental_id, amount, payment_date));
            }
            return rentals;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rentals;
    }

    public ObservableList<DailyExpenses> getAllDailyExpensesData() {
        JDBC j = new JDBC();
        ObservableList<DailyExpenses> dailyexpenses = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM daily_expenses");
            while (rset.next()) {
                int id = rset.getInt(1);
                String date = rset.getString(2);
                Double amount = rset.getDouble(3);
                String description = rset.getString(4);

                dailyexpenses.add(new DailyExpenses(id, date, amount, description));
            }
            return dailyexpenses;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dailyexpenses;
    }

    public ObservableList<CashPayment> getAllCashPaymentData() {
        JDBC j = new JDBC();
        String ch_payee = "";
        if (cmb_cpayee.getValue() != null) {
            ch_payee = cmb_cpayee.getValue().toString();
        }
        
        
        ObservableList<CashPayment> cashpayment = FXCollections.observableArrayList();
        try {
            ResultSet rset = null;
            if (cPaymentSearchKey == null) {
                rset = j.getData("SELECT * FROM cash_payments");
            }
            else {
                rset = j.getData("SELECT * FROM cash_payments WHERE payee ='"+ ch_payee +"'");
                cPaymentSearchKey = null;
            }
            while (rset.next()) {
                Integer id = rset.getInt(1);
                Double amount = rset.getDouble(2);
                String date = rset.getString(3);
                String payee = rset.getString(4);

                cashpayment.add(new CashPayment(id, amount, date, payee));
            }
            return cashpayment;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cashpayment;
    }

    public ObservableList<ChequePayments> getAllChequePaymentData() {
        JDBC j = new JDBC();
        ObservableList<ChequePayments> chequepayment = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM cheque_payments");
            while (rset.next()) {
                String id = rset.getString(1);
                Double amount = rset.getDouble(2);
                String payee = rset.getString(3);
                String issuedate = rset.getString(4);
                String validdate = rset.getString(5);

                chequepayment.add(new ChequePayments(id, amount, payee, issuedate, validdate));
            }
            return chequepayment;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return chequepayment;
    }

    /**
     * load the electricity bills table.
     */
    public void loadElecBillTable() {
//        table_electricity_bills.getColumns().clear();

        TableColumn<ElectricityBill, String> bill_no_col = new TableColumn<>("Bill number");
        bill_no_col.setCellValueFactory(new PropertyValueFactory<>("ebill_no"));

        TableColumn<ElectricityBill, Double> amount_col = new TableColumn<>("Amount");
        amount_col.setCellValueFactory(new PropertyValueFactory<>("eamount"));

        TableColumn<ElectricityBill, Integer> units_col = new TableColumn<>("Units");
        units_col.setCellValueFactory(new PropertyValueFactory<>("eunits"));

        TableColumn<ElectricityBill, String> payment_date_col = new TableColumn<>("Payment date");
        payment_date_col.setCellValueFactory(new PropertyValueFactory<>("payment_date"));

        table_electricity_bills.setItems(getAllElectricityBillData());
        table_electricity_bills.getColumns().addAll(bill_no_col, amount_col, units_col, payment_date_col);
    }

    /**
     * load the water bills table.
     */
    public void loadWaterBillTable() {
        TableColumn<WaterBill, String> bill_no_col = new TableColumn<>("Bill number");
        bill_no_col.setCellValueFactory(new PropertyValueFactory<>("wbill_no"));

        TableColumn<WaterBill, Double> amount_col = new TableColumn<>("Amount");
        amount_col.setCellValueFactory(new PropertyValueFactory<>("wamount"));

        TableColumn<WaterBill, Integer> units_col = new TableColumn<>("Units");
        units_col.setCellValueFactory(new PropertyValueFactory<>("wunits"));

        TableColumn<WaterBill, String> payment_date_col = new TableColumn<>("Payment date");
        payment_date_col.setCellValueFactory(new PropertyValueFactory<>("payment_date"));
        // System.out.println(getAllWaterBillData());
        table_water_bills.setItems(getAllWaterBillData());
        table_water_bills.getColumns().addAll(bill_no_col, amount_col, units_col, payment_date_col);
    }

    /**
     * load the rentals table.
     */
    public void loadRentalsTable() {
        TableColumn<RentalPayment, Integer> id_col = new TableColumn<>("Rental id");
        id_col.setCellValueFactory(new PropertyValueFactory<>("rid"));

        TableColumn<RentalPayment, Double> amount_col = new TableColumn<>("Amount");
        amount_col.setCellValueFactory(new PropertyValueFactory<>("ramount"));

        TableColumn<RentalPayment, String> date_col = new TableColumn<>("Payment Date");
        date_col.setCellValueFactory(new PropertyValueFactory<>("rpayment_date"));

        //System.out.println("KKKKKKK");
        table_rentals.setItems(getAllRentalsData());
        table_rentals.getColumns().addAll(id_col, amount_col, date_col);
    }

    /**
     * load the dailyexpenses table.
     */
    public void loadDailyExpensesTable() {
        TableColumn<DailyExpenses, Integer> id_col = new TableColumn<>("Expense id");
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<DailyExpenses, String> date_col = new TableColumn<>("Date");
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<DailyExpenses, Double> amount_col = new TableColumn<>("Amount");
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<DailyExpenses, String> description_col = new TableColumn<>("Description");
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));

        table_expenses.setItems(getAllDailyExpensesData());
        table_expenses.getColumns().addAll(id_col, date_col, amount_col, description_col);
    }

    /**
     * load the cash payments table.
     */
    public void loadCashPaymentsTable() {
        TableColumn<CashPayment, Integer> id_col = new TableColumn<>("Cash Payment id");
        id_col.setCellValueFactory(new PropertyValueFactory<>("chid"));

        TableColumn<CashPayment, Double> amount_col = new TableColumn<>("Amount");
        amount_col.setCellValueFactory(new PropertyValueFactory<>("chamount"));

        TableColumn<CashPayment, String> date_col = new TableColumn<>("Date");
        date_col.setCellValueFactory(new PropertyValueFactory<>("chdate"));

        TableColumn<CashPayment, String> payee_col = new TableColumn<>("Payee");
        payee_col.setCellValueFactory(new PropertyValueFactory<>("chpayee"));
        //System.out.println("SSS");
        //System.out.println(getAllElectricityBillData());
        table_cash_payment.setItems(getAllCashPaymentData());
        table_cash_payment.getColumns().clear();
        table_cash_payment.getColumns().addAll(id_col, amount_col, date_col, payee_col);
    }

    /**
     * load the cheque payments table.
     */
    public void loadChequePaymentsTable() {
        TableColumn<ChequePayments, String> id_col = new TableColumn<>("Cheque number");
        id_col.setCellValueFactory(new PropertyValueFactory<>("chqno"));

        TableColumn<ChequePayments, Double> amount_col = new TableColumn<>("Amount");
        amount_col.setCellValueFactory(new PropertyValueFactory<>("chqamout"));

        TableColumn<ChequePayments, String> payee_col = new TableColumn<>("Payee");
        payee_col.setCellValueFactory(new PropertyValueFactory<>("chqpayee"));

        TableColumn<ChequePayments, String> idate_col = new TableColumn<>("Issue Date");
        idate_col.setCellValueFactory(new PropertyValueFactory<>("chqissuedate"));

        TableColumn<ChequePayments, String> vdate_col = new TableColumn<>("Valid Date");
        vdate_col.setCellValueFactory(new PropertyValueFactory<>("chqvaliddate"));

        //System.out.println("SSS");
        //System.out.println(getAllElectricityBillData());
        table_cheque_payments.setItems(getAllChequePaymentData());
        table_cheque_payments.getColumns().clear();
        table_cheque_payments.getColumns().addAll(id_col, amount_col, payee_col, idate_col, vdate_col);
    }

    @FXML
    private void addElecBill(ActionEvent event) {
        //btn_addelecbill.setVisible(true);
        
        if (!isNumbersDoubleOnly(txt_eamount.getText()) || !isNumbersIntOnly(txt_eunits.getText()) || !AlphaNumeric(txt_ebill_no.getText())) {
            if (!isNumbersDoubleOnly(txt_eamount.getText())) {
                lbl_emount.setText("Amount must be a valid Numeric Value & required");
                lbl_emount.setVisible(true);
            } else {
                lbl_emount.setVisible(false);
            }

            if (!isNumbersIntOnly(txt_eunits.getText())) {
                lbl_eunits.setText("Unit must be a valid Numeric Value & required");
                lbl_eunits.setVisible(true);
            } else  {
                lbl_eunits.setVisible(false);
            }

            if (!AlphaNumeric(txt_ebill_no.getText())) {
                lbl_ebill.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_ebill.setVisible(true);
            } else {
                lbl_ebill.setVisible(false);
            }
            if(txt_edatep.getValue()==null){
                lbl_edate.setText("Date connot be empty");
                lbl_edate.setVisible(true);
            }
            else{
                lbl_edate.setVisible(false);
            }
        } else {

            String BillNo = txt_ebill_no.getText();
            double Amount = Double.parseDouble(txt_eamount.getText());
            int units = Integer.parseInt(txt_eunits.getText());
            String payment_date = txt_edatep.getValue().toString();
            if(db.setAddData("INSERT INTO electricity_bill(bill_no,Amount,units,payment_date) VALUES('" + BillNo + "','" + Amount + "','" + units + "','" + payment_date + "')"))
            {
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Bill Adding Request");
            alert.setContentText("Electricity Billing Information is added Successfuly!!!");
            alert.showAndWait();
            ObservableList<ElectricityBill> list = getAllElectricityBillData();
            table_electricity_bills.setItems(list);
            
            lbl_emount.setVisible(false);
            lbl_eunits.setVisible(false);
            lbl_ebill.setVisible(false);
            lbl_edate.setVisible(false);
                txt_ebill_no.setText("");
                txt_eamount.setText("");
                txt_eunits.setText("");
                txt_edatep.setValue(null);
            }
            else{
                Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Bill Adding Request");
            alert.setContentText("Adding Electricity Billing Information is unsuccessfull!!!");
            alert.showAndWait();
            LOG.info("Adding Electricity Billing Information is unsuccessfull!!!");
            }
            // }
        }

    }

    @FXML
    private void addwaterbill(ActionEvent event) {
        if (!isNumbersDoubleOnly(txt_wamount.getText()) || !isNumbersIntOnly(txt_wunits.getText()) || !AlphaNumeric(txt_wbill_no.getText())) {
            if (!isNumbersDoubleOnly(txt_wamount.getText())) {
                lbl_wamount.setText("Amount must be a valid Numeric Value & required");
                lbl_wamount.setVisible(true);
            } else {
                lbl_wamount.setVisible(false);
            }

            if (!isNumbersIntOnly(txt_wunits.getText())) {
                lbl_wunits.setText("Unit must be a valid Numeric Value & required");
                lbl_wunits.setVisible(true);
            } else {
                lbl_wunits.setVisible(false);
            }

            if (!AlphaNumeric(txt_wbill_no.getText())) {
                lbl_wbill.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_wbill.setVisible(true);
            } else {
                lbl_wbill.setVisible(false);
            }
            if(txt_wdatep.getValue()==null){
                lbl_wdate.setText("Date cannot be empty");
                lbl_wdate.setVisible(true);
            }
            else{
                lbl_wdate.setVisible(false);
            }
        } //if (!checkWaterBillEmptyFields()) {
        else {
            double Amount = Double.parseDouble(txt_wamount.getText());
            String BillNo = txt_wbill_no.getText();
            int units = Integer.parseInt(txt_wunits.getText());
            String payment_date = txt_wdatep.getValue().toString();
            if(db.setAddData("INSERT INTO water_bill(bill_no,amount,units,payment_date) VALUES('" + BillNo + "','" + Amount + "','" + units + "','" + payment_date + "')"))
            {    
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Bill Adding Request");
            alert.setContentText("Water Billing Information is added Successfuly!!!");
            alert.showAndWait();
            ObservableList<WaterBill> list = getAllWaterBillData();
            table_water_bills.setItems(list);
            lbl_wamount.setVisible(false);
            lbl_wunits.setVisible(false);
            lbl_wbill.setVisible(false);
            lbl_wdate.setVisible(false);
                txt_wbill_no.setText("");
                txt_wamount.setText("");
                txt_wunits.setText("");
                txt_wdatep.setValue(null);
                // }
            }
            else{
                Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Bill Adding Request");
            alert.setContentText("Adding Electricity Billing Information is unsuccessfull!!!");
            alert.showAndWait();
            }
        }
    }

    @FXML
    private void addrental(ActionEvent event) {
        if (!isNumbersDoubleOnly(txt_ramount.getText())) {
            if (!isNumbersDoubleOnly(txt_ramount.getText())) {
                lbl_ramount.setText("Amount must be a valid Numeric Value & required");
                lbl_ramount.setVisible(true);
            } else {
                lbl_ramount.setVisible(false);
            }
            if(txt_rdatep.getValue()==null){
                lbl_rdate.setText("Date cannot be empty");
                lbl_rdate.setVisible(true);
            }
            else{
                lbl_rdate.setVisible(false);
            }
        } else {
            double Amount = Double.parseDouble(txt_ramount.getText());
            String payment_date = txt_rdatep.getValue().toString();
            db.setData("INSERT INTO rentals(amount,payment_date) VALUES('" + Amount + "','" + payment_date + "')");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Bill Adding Request");
            alert.setContentText("Rental Billing Information is added Successfuly!!!");
            alert.showAndWait();
            ObservableList<RentalPayment> list = getAllRentalsData();
            table_rentals.setItems(list);
            lbl_ramount.setVisible(false);
            lbl_rdate.setVisible(false);
            txt_rid.setText("");
        txt_ramount.setText("");
        txt_rdatep.setValue(null);
        }
    }

    @FXML
    private void addDailyexpenses(ActionEvent event) {
        if (!isNumbersDoubleOnly(txt_damount.getText()) || !AlphaNumeric(txt_ddescription.getText())) {
            if (!isNumbersDoubleOnly(txt_damount.getText())) {
                lbl_damount.setText("Amount must be a valid Numeric Value & required");
                lbl_damount.setVisible(true);
            } else {
                lbl_damount.setVisible(false);
            }
            if (!AlphaNumeric(txt_ddescription.getText())) {
                lbl_ddescription.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_ddescription.setVisible(true);
            } else {
                lbl_ddescription.setVisible(false);
            }
            if(txt_ddate.getValue()==null){
                lbl_ddate.setText("Date cannot be empty");
                lbl_ddate.setVisible(true);
            }
            else{
                lbl_ddate.setVisible(false);
            }
        } else {
            String date = txt_ddate.getValue().toString();
            double Amount = Double.parseDouble(txt_damount.getText());
            String description = txt_ddescription.getText();
            db.setData("INSERT INTO daily_expenses(date,amount,description) VALUES('" + date + "','" + Amount + "','" + description + "')");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Bill Adding Request");
            alert.setContentText("Daily Expenses Billing Information is added Successfuly!!!");
            alert.showAndWait();
            ObservableList<DailyExpenses> list = getAllDailyExpensesData();
            table_expenses.setItems(list);
            lbl_ddate.setVisible(false);
             lbl_ddescription.setVisible(false);
              lbl_damount.setVisible(false);
            txt_damount.setText("");
            txt_ddate.setValue(null);
            txt_ddescription.setText("");
            txt_did.setText("");
        }
    }

    @FXML
    private void addCashPayments() {
        if (!isNumbersDoubleOnly(txt_cash_amount.getText()) || !AlphaNumeric(txt_cash_payee.getText())) {
            if (!isNumbersDoubleOnly(txt_cash_amount.getText())) {
                lbl_cshamount.setText("Amount must be a valid Numeric Value & required");
                lbl_cshamount.setVisible(true);
            } else {
                lbl_cshamount.setVisible(false);
            }
            if (!AlphaNumeric(txt_cash_payee.getText())) {
                lbl_cshpayee.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_cshpayee.setVisible(true);
            } else {
                lbl_cshpayee.setVisible(false);
            }
            if(txt_cash_date.getValue()==null){
                lbl_cshdate.setText("Date cannot be empty");
                lbl_cshdate.setVisible(true);
            }
            else{
                lbl_cshdate.setVisible(false);
            }
        } else {
            double Amount = Double.parseDouble(txt_cash_amount.getText());
            String date = txt_cash_date.getValue().toString();
            String payee = txt_cash_payee.getText();
            db.setData("INSERT INTO cash_payments(amount,date,payee) VALUES('" + Amount + "','" + date + "','" + payee + "')");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Payment Adding Request");
            alert.setContentText("Cash payment Information is added Successfuly!!!");
            alert.showAndWait();
            ObservableList<CashPayment> list = getAllCashPaymentData();
            table_cash_payment.setItems(list);
            lbl_cshdate.setVisible(false);
            lbl_cshpayee.setVisible(false);
            lbl_cshamount.setVisible(false);
        txt_cash_amount.setText("");
        txt_cash_payee.setText("");
        txt_cash_id.setText("");
        txt_cash_date.setValue(null);
        
        }
    }

    public void addChequePayments() {

        if (!isNumbersDoubleOnly(txt_cheque_amount.getText()) || !AlphaNumeric(txt_cheque_payee.getText()) || !AlphaNumeric(txt_cheque_id.getText())) {
            if (!isNumbersDoubleOnly(txt_cheque_amount.getText())) {
                lbl_chqamount.setText("Amount must be a valid Numeric Value & required");
                lbl_chqamount.setVisible(true);
            } else {
                lbl_chqamount.setVisible(false);
            }
            if (!AlphaNumeric(txt_cheque_payee.getText())) {
                lbl_chqpayee.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_chqpayee.setVisible(true);
            } else {
                lbl_chqpayee.setVisible(false);
            }
            if (!AlphaNumeric(txt_cheque_id.getText())) {
                lbl_chqno.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_chqno.setVisible(true);
            } else {
                lbl_chqno.setVisible(false);
            }
            if(txt_issue.getValue()==null){
                lbl_chqissued.setText("Date cannot be empty");
                lbl_chqissued.setVisible(true);
            }
            else{
                lbl_chqissued.setVisible(false);
            }
            if(txt_valid.getValue()==null){
                lbl_validd.setText("Date cannot be empty");
                lbl_validd.setVisible(true);
            }
            else{
                lbl_validd.setVisible(false);
            }
            
        } else {
            String chequeNo = txt_cheque_id.getText();
            double Amount = Double.parseDouble(txt_cheque_amount.getText());
            String payee = txt_cheque_payee.getText();
            String issuedate = txt_issue.getValue().toString();
            String validdate = txt_valid.getValue().toString();
           if( db.setAddData("INSERT INTO cheque_payments(cheque_no,amount,payee,issue_date,valid_after) VALUES('" + chequeNo + "','" + Amount + "','" + payee + "','" + issuedate + "','" + validdate + "')"))
           {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Payment Adding Request");
            alert.setContentText("Cheque payment Information is added Successfuly!!!");
            alert.showAndWait();
            ObservableList<ChequePayments> list = getAllChequePaymentData();
            table_cheque_payments.setItems(list);
            lbl_chqamount.setVisible(false);
            lbl_chqpayee.setVisible(false);
            lbl_chqno.setVisible(false);
            lbl_chqissued.setVisible(false);
            lbl_validd.setVisible(false);
            txt_cheque_id.setText("");
        txt_cheque_amount.setText("");
        txt_cheque_payee.setText("");
        txt_issue.setValue(null);
        txt_valid.setValue(null);
           }
           else{
                Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Bill Adding Request");
            alert.setContentText("Adding Electricity Billing Information is unsuccessfull!!!");
            alert.showAndWait();
            }
        }
    }

    //Update Electricity Bill
    public void updateElectricityBill() {
        if (!isNumbersDoubleOnly(txt_eamount.getText()) || !isNumbersIntOnly(txt_eunits.getText()) || !AlphaNumeric(txt_ebill_no.getText())) {
            if (!isNumbersDoubleOnly(txt_eamount.getText())) {
                lbl_emount.setText("Amount must be a valid Numeric Value & required");
                lbl_emount.setVisible(true);
            } else {
                lbl_emount.setVisible(false);
            }

            if (!isNumbersIntOnly(txt_eunits.getText())) {
                lbl_eunits.setText("Unit must be a valid Numeric Value & required");
                lbl_eunits.setVisible(true);
            } else {
                lbl_eunits.setVisible(false);
            }

            if (!AlphaNumeric(txt_ebill_no.getText())) {
                lbl_ebill.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_ebill.setVisible(true);
            } else {
                lbl_ebill.setVisible(false);
            }
            if(txt_edatep.getValue()==null){
                lbl_edate.setText("Date connot be empty");
                lbl_edate.setVisible(true);
            }
            else{
                lbl_edate.setVisible(false);
            }
        } else {
            String BillNo = txt_ebill_no.getText();
            double Amount = Double.parseDouble(txt_eamount.getText());
            int units = Integer.parseInt(txt_eunits.getText());
            String payment_date = txt_edatep.getValue().toString();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Update Alert"); 
            alert.setHeaderText("Bill Update Confirmation");
            alert.setContentText("Do you really want to Update this Billing Information ?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
               if( db.setAddData("UPDATE electricity_bill SET bill_no='"+BillNo+"', Amount=" + Amount + ",units=" + units + ",payment_date='" + payment_date + "' WHERE bill_no='" + currentEbill + "'")){
                ObservableList<ElectricityBill> list = getAllElectricityBillData();
                table_electricity_bills.setItems(list);
//              loadElecBillTable();
                lbl_ebill.setVisible(false);
            lbl_emount.setVisible(false);
            lbl_eunits.setVisible(false);
            lbl_edate.setVisible(false);
            txt_ebill_no.setText("");
        txt_eamount.setText("");
        txt_eunits.setText("");
        txt_edatep.setValue(null);
        btn_addelecbill.setVisible(true);
               }
             else {
                Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Information Alert");
            alert1.setHeaderText("Bill Adding Request");
            alert1.setContentText("Adding Electricity Billing Information is unsuccessfull!!!");
            alert1.showAndWait();
            }
            }
        }
        
    }

    //Update Water Bill
    public void updateWaterBill() {
        if (!isNumbersDoubleOnly(txt_wamount.getText()) || !isNumbersIntOnly(txt_wunits.getText()) || !AlphaNumeric(txt_wbill_no.getText())) {
            if (!isNumbersDoubleOnly(txt_wamount.getText())) {
                lbl_wamount.setText("Amount must be a valid Numeric Value & required");
                lbl_wamount.setVisible(true);
            } else {
                lbl_wamount.setVisible(false);
            }

            if (!isNumbersIntOnly(txt_wunits.getText())) {
                lbl_wunits.setText("Unit must be a valid Numeric Value & required");
                lbl_wunits.setVisible(true);
            } else {
                lbl_wunits.setVisible(false);
            }

            if (!AlphaNumeric(txt_wbill_no.getText())) {
                lbl_wbill.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_wbill.setVisible(true);
            } else {
                lbl_wbill.setVisible(false);
            }
            if(txt_wdatep.getValue()==null){
                lbl_wdate.setText("Date cannot be empty");
                lbl_wdate.setVisible(true);
            }
            else{
                lbl_wdate.setVisible(false);
            }
        } else {
            String BillNo = txt_wbill_no.getText();
            double Amount = Double.parseDouble(txt_wamount.getText());
            int units = Integer.parseInt(txt_wunits.getText());
            String payment_date = txt_wdatep.getValue().toString();

            

            // db.setData("UPDATE water_bill SET amount="+Amount+",units="+units+",payment_date='"+payment_date+"' WHERE bill_no='"+BillNo+"'");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Update Alert");
            alert.setHeaderText("Bill Update Confirmation");
            alert.setContentText("Do you really want to Update this Billing Information ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if(db.setAddData("UPDATE water_bill SET bill_no='" + BillNo + "', amount=" + Amount + ",units=" + units + ",payment_date='" + payment_date + "' WHERE bill_no='" + waterWbill + "'")){
                ObservableList<WaterBill> list = getAllWaterBillData();
                table_water_bills.setItems(list);
                lbl_wamount.setVisible(false);
            lbl_wbill.setVisible(false);
            lbl_wunits.setVisible(false);
            lbl_wdate.setVisible(false);
                txt_wbill_no.setText("");
                txt_wamount.setText("");
                txt_wunits.setText("");
                txt_wdatep.setValue(null);
                btn_addwaterbill.setVisible(true);
            } else {
                  Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Information Alert");
            alert1.setHeaderText("Bill Adding Request");
            alert1.setContentText("Adding Electricity Billing Information is unsuccessfull!!!");
            alert1.showAndWait();
            }

        }
        } 
    }
    // Update RentalPayment

    public void updateRentalPayments() {
        if (!isNumbersDoubleOnly(txt_ramount.getText())) {
            if (!isNumbersDoubleOnly(txt_ramount.getText())) {
                lbl_ramount.setText("Amount must be a valid Numeric Value & required");
                lbl_ramount.setVisible(true);
            } else {
                lbl_ramount.setVisible(false);
            }
            if(txt_rdatep.getValue()==null){
                lbl_rdate.setText("Date cannot be empty");
                lbl_rdate.setVisible(true);
            }
            else{
                lbl_rdate.setVisible(false);
            }
        } else {
            String Rentalid = txt_rid.getText();
            String payment_date = txt_rdatep.getValue().toString();
            double Amount = Double.parseDouble(txt_ramount.getText());

            lbl_ramount.setVisible(false);

            // db.setData("UPDATE rentals SET amount="+Amount+",payment_date='"+payment_date+"' WHERE rental_id='"+rental_id+"'");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Update Alert");
            alert.setHeaderText("Bill Update Confirmation");
            alert.setContentText("Do you really want to Update this Billing Information ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                db.setData("UPDATE rentals SET amount=" + Amount + ",payment_date='" + payment_date + "' WHERE rental_id='" + rental_id + "'");
                ObservableList<RentalPayment> list = getAllRentalsData();
                table_rentals.setItems(list);
            } else {

            }
             lbl_ramount.setVisible(false);
            lbl_rdate.setVisible(false);
             txt_rid.setText("");
        txt_ramount.setText("");
        txt_rdatep.setValue(null);
        btn_addrental.setVisible(true);
        }
        
    }

    public void updateDailyExpensesPayments() {
        //String Expensesid = txt_did.getText();
        //DailyExpenses dexp=(DailyExpenses) table_expenses.getSelectionModel().getSelectedItem();
        if (!isNumbersDoubleOnly(txt_damount.getText()) || !AlphaNumeric(txt_ddescription.getText())) {
            if (!isNumbersDoubleOnly(txt_damount.getText())) {
                lbl_damount.setText("Amount must be a valid Numeric Value & required");
                lbl_damount.setVisible(true);
            } else {
                lbl_damount.setVisible(false);
            }
            if (!AlphaNumeric(txt_ddescription.getText())) {
                lbl_ddescription.setText("Bill ID can contain only Numbers and Letters");
                lbl_ddescription.setVisible(true);
            } else {
                lbl_ddescription.setVisible(false);
            }
            if(txt_ddate.getValue()==null){
                lbl_ddate.setText("Date cannot be empty");
                lbl_ddate.setVisible(true);
            }
            else{
                lbl_ddate.setVisible(false);
            }
        } else {
            String Expensesid = txt_did.getText();
            double Amount = Double.parseDouble(txt_damount.getText());
            String payment_date = txt_ddate.getValue().toString();
            String description = txt_ddescription.getText().toString();

            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Update Alert");
            alert.setHeaderText("Bill Update Confirmation");
            alert.setContentText("Do you really want to Update this Billing Information ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //  System.out.println("Hi");
                db.setData("UPDATE daily_expenses SET date='" + payment_date + "',amount=" + Amount + ",description='" + description + "' WHERE id='" + expenses_id + "'");
                ObservableList<DailyExpenses> list = getAllDailyExpensesData();
                table_expenses.setItems(list);
            } else {

            }
            lbl_ddate.setVisible(false);
            lbl_ddescription.setVisible(false);
            lbl_damount.setVisible(false);
            txt_damount.setText("");
        txt_ddate.setValue(null);
        txt_ddescription.setText("");
        txt_did.setText("");
        btn_addexpenses.setVisible(true);
        }
        
    }

    public void updateCashPayments() {
        if (!isNumbersDoubleOnly(txt_cash_amount.getText()) || !AlphaNumeric(txt_cash_payee.getText())) {
            if (!isNumbersDoubleOnly(txt_cash_amount.getText())) {
                lbl_cshamount.setText("Amount must be a valid Numeric Value & required");
                lbl_cshamount.setVisible(true);
            } else {
                lbl_cshamount.setVisible(false);
            }
            if (!AlphaNumeric(txt_cash_payee.getText())) {
                lbl_cshpayee.setText("Bill ID can contain only Numbers and Letters & required");
                lbl_cshpayee.setVisible(true);
            } else {
                lbl_cshpayee.setVisible(false);
            }
            if(txt_cash_date.getValue()==null){
                lbl_cshdate.setText("Date cannot be empty");
                lbl_cshdate.setVisible(true);
            }
            else{
                lbl_cshdate.setVisible(false);
            }
        }else{
        String cashId = Integer.toString(cash_id);
        String payment_date = txt_cash_date.getValue().toString();
        String payee = txt_cash_payee.getText();
        double Amount = Double.parseDouble(txt_cash_amount.getText());

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Bill Update Confirmation");
        alert.setContentText("Do you really want to Update this Billing Information ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData("UPDATE cash_payments SET date='" + payment_date + "',amount=" + Amount + ",payee='" + payee + "' WHERE cash_id='" + cash_id + "'");
            ObservableList<CashPayment> list = getAllCashPaymentData();
            table_cash_payment.setItems(list);
           // System.out.println(txt_cash_id.getText());
            //System.out.println("UPDATE cash_payments SET date='" + payment_date + "',amount=" + Amount + ",payee='" + payee + "' WHERE cash_id='" + cash_id + "'");

        } else {

        }
        lbl_cshdate.setVisible(false);
        lbl_cshpayee.setVisible(false);
        lbl_cshamount.setVisible(false);
        txt_cash_amount.setText("");
        txt_cash_payee.setText("");
        txt_cash_id.setText("");
        txt_cash_date.setValue(null);
        btn_addcashpay.setVisible(true);
        }
    }

    public void updateChequePayments() {
        if (!isNumbersDoubleOnly(txt_cheque_amount.getText()) || !AlphaNumeric(txt_cheque_payee.getText()) || !AlphaNumeric(txt_cheque_id.getText())) {
            if (!isNumbersDoubleOnly(txt_cheque_amount.getText())) {
                lbl_chqamount.setText("Amount must be a valid Numeric Value & required");
                lbl_chqamount.setVisible(true);
            } else {
                lbl_chqamount.setVisible(false);
            }
            if (!AlphaNumeric(txt_cheque_payee.getText())) {
                lbl_chqpayee.setText("Bill ID can contain only Numbers and Letters");
                lbl_chqpayee.setVisible(true);
            } else {
                lbl_chqpayee.setVisible(false);
            }
            if (!AlphaNumeric(txt_cheque_id.getText())) {
                lbl_chqno.setText("Bill ID can contain only Numbers and Letters");
                lbl_chqno.setVisible(true);
            } else {
                lbl_chqno.setVisible(false);
            }
            if(txt_issue.getValue()==null){
                lbl_chqissued.setText("Date cannot be empty");
                lbl_chqissued.setVisible(true);
            }
            else{
                lbl_chqissued.setVisible(false);
            }
            if(txt_valid.getValue()==null){
                lbl_validd.setText("Date cannot be empty");
                lbl_validd.setVisible(true);
            }
            else{
                lbl_validd.setVisible(false);
            }
        } else {
            String ChequeNo = txt_cheque_id.getText();
            double Amount = Double.parseDouble(txt_cheque_amount.getText());
            String Payee = txt_cheque_payee.getText();
            String issue_date = txt_issue.getValue().toString();
            String valid_date = txt_valid.getValue().toString();

            // db.setData("UPDATE water_bill SET amount="+Amount+",units="+units+",payment_date='"+payment_date+"' WHERE bill_no='"+BillNo+"'");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Update Alert");
            alert.setHeaderText("Bill Update Confirmation");
            alert.setContentText("Do you really want to Update this Billing Information ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
               if( db.setAddData("UPDATE cheque_payments SET cheque_no='"+ChequeNo+"', amount='" + Amount + "',payee='" + Payee + "',issue_date='" + issue_date + "',valid_after='" + valid_date + "' WHERE cheque_no='" + chequeDetails + "'")){
                ObservableList<ChequePayments> list = getAllChequePaymentData();
                table_cheque_payments.setItems(list);
                lbl_validd.setVisible(false);
        lbl_chqissued.setVisible(false);
        lbl_chqno.setVisible(false);
        lbl_chqpayee.setVisible(false);
        lbl_chqamount.setVisible(false);
        txt_cheque_id.setText("");
        txt_cheque_amount.setText("");
        txt_cheque_payee.setText("");
        txt_issue.setValue(null);
        txt_valid.setValue(null);
        btn_addchequepay.setVisible(true);
            } else {
                    Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Information Alert");
            alert1.setHeaderText("Bill Adding Request");
            alert1.setContentText("Adding Electricity Billing Information is unsuccessfull!!!");
            alert1.showAndWait();
            }
            
        }
        } 
    }

    public void deleteElectricityBill() {
        String BillNo = txt_ebill_no.getText();
        String sql = "DELETE FROM electricity_bill where bill_no='" + BillNo + "'";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Bill Delete Confirmation");
        alert.setContentText("Do you really want to delete this Billing Information ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData(sql);
            ObservableList<ElectricityBill> list = getAllElectricityBillData();
            table_electricity_bills.setItems(list);
        } else {

        }
        txt_ebill_no.setText("");
        txt_eamount.setText("");
        txt_eunits.setText("");
        txt_edatep.setValue(null);
        btn_addelecbill.setVisible(true);
    }

    public void deleteWaterBill() {
        String BillNo = txt_wbill_no.getText();
        String sql = "DELETE FROM water_bill where bill_no='" + BillNo + "'";

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Bill Delete Confirmation");
        alert.setContentText("Do you really want to delete this Billing Information ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData(sql);
            ObservableList<WaterBill> list = getAllWaterBillData();
            table_water_bills.setItems(list);
        } else {

        }
        txt_wbill_no.setText("");
        txt_wamount.setText("");
        txt_wunits.setText("");
        txt_wdatep.setValue(null);
        btn_addwaterbill.setVisible(true);
    }

    public void deleteRental() {
//        String Rentalid = txt_rid.getText();
        RentalPayment rntl = (RentalPayment) table_rentals.getSelectionModel().getSelectedItem();
        int Rentalid = rntl.getRid();
        String sql = "DELETE FROM rentals where rental_id='" + Rentalid + "'";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Bill Delete Confirmation");
        alert.setContentText("Do you really want to delete this Rental Information ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData(sql);
            ObservableList<RentalPayment> list = getAllRentalsData();
            table_rentals.setItems(list);
        } else {

        }
        txt_rid.setText("");
        txt_ramount.setText("");
        txt_rdatep.setValue(null);
        btn_addrental.setVisible(true);
    }

    public void deleteDailyExpenses() {
        //String Expensesid = txt_did.getText();
        DailyExpenses dexp = (DailyExpenses) table_expenses.getSelectionModel().getSelectedItem();
        int Expensesid = dexp.getId();
        String sql = "DELETE FROM daily_expenses where id='" + Expensesid + "'";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Bill Delete Confirmation");
        alert.setContentText("Do you really want to delete this Billing Information ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData(sql);
            ObservableList<DailyExpenses> list = getAllDailyExpensesData();
            table_expenses.setItems(list);
        } else {

        }
        txt_damount.setText("");
        txt_ddate.setValue(null);
        txt_ddescription.setText("");
        txt_did.setText("");
        btn_addexpenses.setVisible(true);

    }

    public void deleteChequePayments() {
        String chequeNo = txt_cheque_id.getText();
        String sql = "DELETE FROM cheque_payments where cheque_no='" + chequeNo + "'";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Bill Delete Confirmation");
        alert.setContentText("Do you really want to delete this Payment Information ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData(sql);
            ObservableList<ChequePayments> list = getAllChequePaymentData();
            table_cheque_payments.setItems(list);
        } else {

        }
        txt_cheque_id.setText("");
        txt_cheque_amount.setText("");
        txt_cheque_payee.setText("");
        txt_issue.setValue(null);
        txt_valid.setValue(null);
        btn_addchequepay.setVisible(true);
    }

    public void deleteCashPayments() {

        CashPayment chp = (CashPayment) table_cash_payment.getSelectionModel().getSelectedItem();
        int cashNo = chp.getChid();
        String sql = "DELETE FROM cash_payments where cash_id='" + cashNo + "'";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Alert");
        alert.setHeaderText("Bill Delete Confirmation");
        alert.setContentText("Do you really want to delete this Payment Information ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            db.setData(sql);
            ObservableList<CashPayment> list = getAllCashPaymentData();
            table_cash_payment.setItems(list);
        } else {

        }
        txt_cash_amount.setText("");
        txt_cash_payee.setText("");
        txt_cash_id.setText("");
        txt_cash_date.setValue(null);
        btn_addcashpay.setVisible(true);

    }



    public static boolean isNumbersIntOnly(String input) {
        if (input.isEmpty()) {
            return false;
        }

        try {
            int d = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isNumbersDoubleOnly(String input) {

        if (input.isEmpty()) {
            return false;
        }
        try {
            double d = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean AlphaNumeric(String input) {
        if (input.isEmpty()) {
            return false;
        }
        return true;
    }
    public void UtilityBill(){
        acc_finance.setExpandedPane(pane_utility);
    }
     public void Payments(){
        acc_finance.setExpandedPane(pane_payments);
    }
     public void Profit(){
        acc_finance.setExpandedPane(pane_profit);
    }
    

    public void searchelecbill() {
        String fromDate = dtp_efrom.getValue().toString();
        String toDate = dtp_eto.getValue().toString();
        ObservableList<ElectricityBill> list = getSearchElectricityBillData(fromDate, toDate);

        table_electricity_bills.setItems(list);
    }

    public ObservableList<ElectricityBill> getSearchElectricityBillData(String fromDate, String toDate) {
        JDBC j = new JDBC();
        ObservableList<ElectricityBill> electricitybills = FXCollections.observableArrayList();
        try {

            ResultSet rset = j.getData("SELECT * FROM electricity_bill where payment_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
            while (rset.next()) {
                String bill_no = rset.getString(1);
                Double amount = rset.getDouble(2);
                int units = rset.getInt(3);
                String payment_date = rset.getString(4);

                electricitybills.add(new ElectricityBill(bill_no, amount, units, payment_date));
            }
            return electricitybills;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return electricitybills;

    }

    public ObservableList<WaterBill> getSearchWaterBillData(String fromDate, String toDate) {
        JDBC j = new JDBC();
        ObservableList<WaterBill> waterbills = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM water_bill where payment_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
            while (rset.next()) {
                String bill_no = rset.getString(1);
                Double amount = rset.getDouble(2);
                int units = rset.getInt(3);
                String payment_date = rset.getString(4);

                waterbills.add(new WaterBill(bill_no, amount, units, payment_date));
            }
            return waterbills;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return waterbills;
    }

    public void searchwaterbill() {
        String fromDate = dtp_wfrom.getValue().toString();
        String toDate = dtp_wto.getValue().toString();
        ObservableList<WaterBill> list = getSearchWaterBillData(fromDate, toDate);

        table_water_bills.setItems(list);
    }

    public ObservableList<RentalPayment> getSearchRentalsData(String fromDate, String toDate) {
        JDBC j = new JDBC();
        ObservableList<RentalPayment> rentals = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM rentals where payment_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
            while (rset.next()) {
                Integer rental_id = rset.getInt(1);
                Double amount = rset.getDouble(2);
                String payment_date = rset.getString(3);

                rentals.add(new RentalPayment(rental_id, amount, payment_date));
            }
            return rentals;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rentals;
    }

    public void searchRentals() {
        String fromDate = dtp_rfrom.getValue().toString();
        String toDate = dtp_rto.getValue().toString();
        ObservableList<RentalPayment> list = getSearchRentalsData(fromDate, toDate);

        table_rentals.setItems(list);
    }

    public ObservableList<DailyExpenses> getSearchDailyExpensesData(String ddate) {
        JDBC j = new JDBC();
        ObservableList<DailyExpenses> dailyexpenses = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM daily_expenses where date = '" + ddate + "'");
            while (rset.next()) {
                int id = rset.getInt(1);
                String date = rset.getString(2);
                Double amount = rset.getDouble(3);
                String description = rset.getString(4);

                dailyexpenses.add(new DailyExpenses(id, date, amount, description));
            }
            return dailyexpenses;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dailyexpenses;
    }

    public void searchDailyExpenses() {
        String Date = dtp_ddate.getValue().toString();

        ObservableList<DailyExpenses> list = getSearchDailyExpensesData(Date);

        table_expenses.setItems(list);
    }

    public ObservableList<searchCashPayee> getSearchCashPaymentData() {
        JDBC j = new JDBC();
        ObservableList<searchCashPayee> cashpayment = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT distinct payee FROM cash_payments ");
            while (rset.next()) {

                String payee = rset.getString(1);

                cashpayment.add(new searchCashPayee(payee));
            }
            return cashpayment;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cashpayment;
    }
    public ObservableList<searchCashPayee> setSearchCashPaymentData() {
        JDBC j = new JDBC();
        String ch_payee = cmb_cpayee.getValue().toString();
        ObservableList<searchCashPayee> cashpayment = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM cash_payments WHERE payee ='"+ ch_payee +"' ");
            while (rset.next()) {

                String payee = rset.getString(1);

                cashpayment.add(new searchCashPayee(payee));
                
            }
            return cashpayment;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cashpayment;
    }

    static String cPaymentSearchKey = null;
    public void searchCashPayments() {
        String payee = cmb_cpayee.getSelectionModel().getSelectedItem().toString();
        cPaymentSearchKey = payee;
        loadCashPaymentsTable();
        //table_cash_payment.getColumns().clear();
//        ObservableList<searchCashPayee> list = setSearchCashPaymentData();
//
//        table_cash_payment.setItems(list);
    }

    public ObservableList<searchChequePayee> getSearchChequePaymentData() {
        JDBC j = new JDBC();
        ObservableList<searchChequePayee> chequepayment = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT distinct payee FROM cheque_payments");
            while (rset.next()) {

                String payee = rset.getString(1);

                chequepayment.add(new searchChequePayee(payee));
            }
            return chequepayment;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return chequepayment;
    }
    public ObservableList<ChequePayments> setSearchChequePaymentData() {
        JDBC j = new JDBC();
        String chq_payee = cmb_chpayee.getValue().toString();
        ObservableList<ChequePayments> chequepayment = FXCollections.observableArrayList();
        try {
            ResultSet rset = j.getData("SELECT * FROM cheque_payments WHERE payee ='"+ chq_payee +"'");
            while (rset.next()) {

                String id = rset.getString(1);
                Double amount = rset.getDouble(2);
                String payee = rset.getString(3);
                String issuedate = rset.getString(4);
                String validdate = rset.getString(5);

                chequepayment.add(new ChequePayments(id,amount,payee,issuedate,validdate));
            }
            return chequepayment;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return chequepayment;
    }

        static String chqPaymentSearchKey = null;
    public void searchChequePayments() {
        //String payee = cmb_chpayee.getSelectionModel().getSelectedItem().toString();
        //chqPaymentSearchKey = payee;
        //loadChequePaymentsTable();
        table_cheque_payments.setItems(setSearchChequePaymentData());
    }

    //profit 
    //load table 
    public ObservableList<Profit> getAllProfit() {
        JDBC j = new JDBC();
        ObservableList<Profit> profits = FXCollections.observableArrayList();
        
        Date d1 = Date.valueOf(dtp_pfrom.getValue());
        Date d2 = Date.valueOf(dtp_pto.getValue());
        
        try {
//            ResultSet rseti = j.getData("SELECT date,net_tot FROM invoice WHERE date BETWEEN '"+dtp_pfrom.getValue()+"' AND '"+dtp_pto.getValue()+"'");
            ResultSet rseti = j.getData("SELECT date,net_tot FROM invoice WHERE date BETWEEN '"+ d1 +"' AND '"+ d2 +"'");
            ResultSet rsetw = j.getData("SELECT payment_date,amount FROM water_bill WHERE payment_date BETWEEN '"+dtp_pfrom.getValue()+"' AND '"+dtp_pto.getValue()+"'");
            ResultSet rsete = j.getData("SELECT payment_date,Amount FROM electricity_bill WHERE payment_date BETWEEN '"+dtp_pfrom.getValue()+"' AND '"+dtp_pto.getValue()+"'");
            ResultSet rsetd = j.getData("SELECT date,amount FROM daily_expenses WHERE date BETWEEN '"+dtp_pfrom.getValue()+"' AND '"+dtp_pto.getValue()+"'");
            ResultSet rsetr = j.getData("SELECT payment_date,amount FROM rentals WHERE payment_date BETWEEN '"+dtp_pfrom.getValue()+"' AND '"+dtp_pto.getValue()+"'");
            double tot_Dincome = 0, tot_Dexpense = 0, tot_income = 0, tot_expense = 0, Dprofit = 0, profit = 0;
            while (rsetd.next()) { //1=date 2=amount
                //System.out.println("s" + rsetd.getDouble(2));
                tot_Dexpense = rsetd.getDouble(2);
                tot_Dincome = 0;
                rseti = j.getData("SELECT date,net_tot FROM invoice");
                while (rseti.next()) {
                    if (rseti.getString(1).equals(rsetd.getString(1))) {
                        tot_Dincome = tot_Dincome + rseti.getDouble(2);
                    }
                }
                rsetw = j.getData("SELECT payment_date,amount FROM water_bill");
                while (rsetw.next()) {
                    if (rsetw.getString(1).equals(rsetd.getString(1))) {
                        tot_Dexpense = tot_Dexpense + rsetw.getDouble(2);
                    }
                }
                rsete = j.getData("SELECT payment_date,Amount FROM electricity_bill");
                while (rsete.next()) {
                    if (rsete.getString(1).equals(rsetd.getString(1))) {
                        tot_Dexpense = tot_Dexpense + rsete.getDouble(2);
                    }
                }

                rsetr = j.getData("SELECT payment_date,amount FROM rentals");
                while (rsetr.next()) {
                    if (rsetr.getString(1).equals(rsetd.getString(1))) {
                        tot_Dexpense = tot_Dexpense + rsetr.getDouble(2);
                    }
                }

                tot_income = tot_income + tot_Dincome;
                tot_expense = tot_expense + tot_Dexpense;

                profit = tot_income - tot_expense;
                Dprofit = tot_Dincome - tot_Dexpense;
                
                //System.out.println(" income " + tot_income+" expenses "+tot_expense+" daily income "+tot_Dincome+" daily expense"+tot_Dexpense);
                //System.out.println("d pro : " + Dprofit + " Profit   " + profit );
                profits.add(new Profit(rsetd.getString(1), tot_Dincome, tot_Dexpense, Dprofit));

            }
            txt_pincome.setText(String.valueOf(tot_income));
            txt_pexpenses.setText(String.valueOf(tot_expense));
            txt_pprofit.setText(String.valueOf(profit));
            
            return profits;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return profits;
    }

    public void loadProfitTable() {
        TableColumn<Profit, String> date_col = new TableColumn<>("Date");
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Profit, Double> income_col = new TableColumn<>("Income");
        income_col.setCellValueFactory(new PropertyValueFactory<>("income"));

        TableColumn<Profit, Double> expense_col = new TableColumn<>("Expenses");
        expense_col.setCellValueFactory(new PropertyValueFactory<>("expense"));

        TableColumn<Profit, Double> profit_col = new TableColumn<>("Profit");
        profit_col.setCellValueFactory(new PropertyValueFactory<>("profit"));
        // System.out.println(getAllWaterBillData());
        //ObservableList<Profit> list = getAllProfit();
        
        //tbl_profit.setItems(list);
        tbl_profit.getColumns().addAll(date_col, income_col, expense_col, profit_col);
    }

    public void searchProfit(){
        ObservableList<Profit> list = getAllProfit();        
        tbl_profit.setItems(list);
    }
}
