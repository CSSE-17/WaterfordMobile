/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;



import static com.sun.deploy.config.JREInfo.clear;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.InvenModel;
import models.OfferModel;
import models.advertising;
//import util.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.EmailUtil;
import util.FormValidate;
import util.JDBC;
//import static sun.net.www.MimeTable.loadTable;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AdvertisingController implements Initializable {

    static final Logger LOG = LoggerFactory.getLogger(PasswordRecoveryController.class);

    @FXML
    private TextField txt_offer_id;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_discount;
    @FXML
    private TextField txt_description;
    @FXML
    private DatePicker datepicker_from;
    @FXML
    private DatePicker datepicker_to;
    @FXML
    private TableView table_advertising;
    @FXML
    private TextField txt_offer_filter;
    @FXML
    private TableView table_Offer;
     @FXML
    private TextField offers_Email;
    @FXML
    private TextArea message_Txt;

    
     //public static advertising selectedoffer;

     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //generateOfferID();
     
       loadOfferTables();
        
       
         table_Offer.getSelectionModel().selectedItemProperty().addListener((Observable,oldValue,newValue) -> {
         OfferModel offer = (OfferModel) Observable.getValue();
         lorddRowFromtable();
             if (offer == null){
              return;   
             }
         });
       loadOfferTables();
     }
    
    public void loadOfferTables(){
        
    TableColumn<OfferModel, String> offer_id_col = new TableColumn<>("Offer_ID");
    offer_id_col.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
    
    TableColumn<OfferModel,String> name_col = new TableColumn<>("Name");
    name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
    
    TableColumn<OfferModel, String> discount_col = new TableColumn<>("Discount");
    discount_col.setCellValueFactory(new PropertyValueFactory<>("Discount"));
    
    TableColumn<OfferModel, String> description_col = new TableColumn<>("Description");
    description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
    
    TableColumn<OfferModel, String> effectivefrom_col = new TableColumn<>("Effectivefrom");
    effectivefrom_col.setCellValueFactory(new PropertyValueFactory<>("effectivefrom"));
    
    TableColumn<OfferModel, String> effectiveto_col = new TableColumn<>("Effectiveto");
    effectiveto_col.setCellValueFactory(new PropertyValueFactory<>("effectiveto"));
  
    ObservableList<OfferModel> offers = getAllOffers();
        FilteredList<OfferModel> filteredoffer = new FilteredList<>(offers, p -> true);
    
    
    
   
     table_Offer.getColumns().clear();
     table_Offer.setItems(getAllOffers());
     table_Offer.getColumns().addAll(offer_id_col,name_col,discount_col,description_col,effectivefrom_col,effectiveto_col);
     
    }    
                
public ObservableList<OfferModel> getAllOffers(){
       ObservableList<OfferModel> Offers = FXCollections.observableArrayList();

        JDBC j = new JDBC();
        try {
        ResultSet rset = j.getData("SELECT *FROM offers");
        while(rset.next()){
            String offer_id = rset.getString(1);
            String name = rset.getString(2);
            Float discount = rset.getFloat(3);
            String description = rset.getString(4);
            String effectivefrom = rset.getString(5);
            String effectiveto = rset.getString(6);
            
            Offers.add(new OfferModel(offer_id, name, discount, description, effectivefrom, effectiveto));
        }
        return Offers; 
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Offers;
}
    public void saveoffers(){
    if(!checkEmptyFields()){

        System.out.println("Save called");
    String offer_id = txt_offer_id.getText();
    String name = txt_name.getText();
    float discount = Float.parseFloat(txt_discount.getText());
    String description = txt_description.getText();
    String date_effectivefrom = datepicker_from.getValue().toString();
    String date_effectiveto = datepicker_to.getValue().toString();

      advertising adv = new advertising(offer_id, name, discount, description, date_effectivefrom,date_effectiveto);
      adv.addNewAdvertising(adv);
      LOG.info(" New Offer added ");

    }
    loadOfferTables();
    clear();
    }
   private void lorddRowFromtable() {
         table_Offer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            OfferModel o = (OfferModel) obs.getValue();
            if (o == null) {
                return;
                }
                String offer_id = o.getOffer_id();
                String name = o.getName();
                Float discount = o.getdiscount();
                String description = o.getDescription();
                String effectivefrom = o.getEffectivefrom();
                String effectiveto= o.getEffectiveto();
                
       
            txt_offer_id.setText(offer_id);
            txt_name.setText(name);
            txt_description.setText(description);
            txt_discount.setText(Float.toString(discount));
            
            datepicker_from.setValue(LocalDate.parse(effectivefrom));
            datepicker_to.setValue(LocalDate.parse(effectiveto));
            
            lorddRowFromtable();
            
        });
    }
    public void updateoffres() {
    //String offer_id = txt_offer_id.getText();
    //String name = txt_name.getText();
    //float discount = Float.parseFloat(txt_discount.getText());
    //String description = txt_description.getText();
    //String effectivefrom = datepicker_from.getValue().toString();
    //String effectiveto = datepicker_to.getValue().toString(); 
    
    //advertising adv = new advertising(offer_id, name, discount, description,effectivefrom,effectiveto);
    //adv.updateNewAdvertising(adv);
    String offer_id = txt_offer_id.getText();
    String name = txt_name.getText();
    float discount = Float.parseFloat(txt_discount.getText());
    String description = txt_description.getText();
    String effectivefrom = datepicker_from.getValue().toString();
    String effectiveto = datepicker_to.getValue().toString(); 
    
    advertising adv = new advertising(offer_id, name, discount, description,effectivefrom,effectiveto);
    adv.updateNewAdvertising(adv); Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Alert");
        alert.setHeaderText("Item Update Confirmation");
        alert.setContentText("Do you want to Update this offers ? ");
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("pooup");
         if(result.get() == ButtonType.OK){
             System.out.println("ok");
             JDBC j = new JDBC();
             try {
               adv.setData("UPDATE offers set offer_id='"+offer_id+"',name='"+name+"',discount='"+discount+"', description='"+description+"', effectivefrom='"+effectivefrom+"', effectiveto='"+effectiveto+"' WHERE offer_id='"+offer_id+"'");   
             } catch (Exception e) {
                 e.printStackTrace();
             }
            //adv.setData("UPDATE offers set offer_id='"+offer_id+"',name='"+name+"',discount='"+discount+"', description='"+description+"', effectivefrom='"+effectivefrom+"', effectiveto='"+effectiveto+"' WHERE offer_id='"+offer_id+"'");
            ObservableList<OfferModel> list = getAllOffers();
            table_Offer.setItems(list);
     }
        else{
        
        }
    }
    
     public void deleteoffres() {
         
  
        String offer_id = txt_offer_id.getText();
        
        Alert confDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confDialog.setTitle("Confirm action!");
        confDialog.setHeaderText("Are you sure you want to permanently delete offer " + offer_id + "?");
        Optional<ButtonType> result = confDialog.showAndWait();
        
        if (result.get() != ButtonType.OK) {
            return;
        }
        
       new advertising().deleteAdvertising(offer_id);
       

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Offer " + offer_id + " successfully deleted!");
        alert.showAndWait();
        loadOfferTables();
        
       
    }

        public static boolean isEmptyField(String offer_id){
        int strlen = offer_id.length();
        if (strlen == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(offer_id+"Cannot be empty");
            alert.showAndWait();
            
            return true;
        }
         else {
            return false;
        }
    }
      public void generateOfferID () {
     String offer_id = "";
        Calendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR) % 100;
       int offerCount = new offer().getOfferCount()+1;

        int curr_len = (year +"" + offerCount).length();
        int padding_len = 5 - curr_len;

        offer_id = offer_id + year;
        for (int i = 0; i < padding_len; i++) {
            offer_id = offer_id + 1;
        }
        offer_id = offer_id + offerCount;

        txt_offer_id.setText(offer_id);
   
   }

   
   boolean checkEmptyFields() {
        FormValidate fv = new FormValidate();

        if (fv.isEmptyField(txt_offer_id.getText(), "offer-id")) {
            return true;
        } else if (fv.isEmptyField(txt_name.getText(), " Name")) {
            return true;
        } else if (fv.isEmptyField(txt_description.getText(), "description")) {
            return true;
        } else if (fv.isEmptyField(txt_discount.getText(), "discount")) {
            return true;
        } else if (fv.isEmptyField(datepicker_from.getValue().toString(), "datepicker_from")) {
            return true;
        } 
        else if (fv.isEmptyField(datepicker_to.getValue().toString(), "datepicker_to")) {
            return true;
        } 
        
        return false;
    }
      public void sendOffers(){


            String htmlText = message_Txt.getText();;
            String email  =   offers_Email.getText();

          if (FormValidate.validateEmail(email) && htmlText.equals("") && email.equals("") ) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText("Invalid email address.");
              alert.showAndWait();
          }else{

            EmailUtil offerSend = new EmailUtil();
            if(!offerSend.sendOffersEmail(htmlText,email)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Email Send Fail ");
                alert.showAndWait();
                LOG.info("Email Send  to " + email);

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Offer Send");
                alert.setHeaderText("Email has been send !");
                alert.showAndWait();
            }

        }


    }
}

    
       

