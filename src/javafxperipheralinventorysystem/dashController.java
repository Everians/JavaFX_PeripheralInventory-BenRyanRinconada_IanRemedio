/*
 *ITP121 INTEGRATIVE PROGRAMMING & TECHNOLOGIES Final / Project. 
 *By: Ben Ryan Rinconada And Ian Remedio
 */
package javafxperipheralinventorysystem;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Filter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafxperipheralinventorysystem.getData.username;

/**
 *
 * @author benry
 */
public class dashController implements Initializable {
    
     @FXML
    private AnchorPane home_form;
    
    @FXML
    private BorderPane main_form;
    
     @FXML
    private Button close_btn;
    
    @FXML
    private Button minimize;

    @FXML
    private Button home_btn;

    @FXML
    private Button signout_btn;

    @FXML
    private Button available_btn;

    @FXML
    private Button assign_btn;

    @FXML
    private TextField available_id;

    @FXML
    private TextField available_Brand;

    @FXML
    private TextField available_Model;

    @FXML
    private ComboBox<?> available_Type;

    @FXML
    private ComboBox<?> available_availablity;

    @FXML
    private ImageView available_ImageView;

    @FXML
    private JFXButton available_import_btn;

    @FXML
    private JFXButton available_update_btn;

    @FXML
    private JFXButton available_clear_btn;

    @FXML
    private JFXButton available_insert_btn;

    @FXML
    private JFXButton available_delete_btn;

    @FXML
    private AnchorPane available_form;

    @FXML
    private TableView<peripheralData> available_tableView;

    @FXML
    private TableColumn<peripheralData, String> available_col_id;

    @FXML
    private TableColumn<peripheralData, String> available_col_brand;

    @FXML
    private TableColumn<peripheralData, String> available_col_model;

    @FXML
    private TableColumn<peripheralData, String> available_col_type;

    @FXML
    private TableColumn<peripheralData, String> available_col_availability;
    

    @FXML
    private TextField available_searchB;

    @FXML
    private AnchorPane assign_form;

    @FXML
    private ComboBox<?> asgn_Brand;

    @FXML
    private ComboBox<?> asgn_Model;

    @FXML
    private JFXButton asgn_btn;

    @FXML
    private ComboBox<?> asgn_Id;

    @FXML
    private TextField asgn_FirstName;
     
    @FXML
    private TextField asgn_lastName;

    @FXML
    private TableView<peripheralData> asgn_tableView;

    @FXML
    private TableColumn<peripheralData, String> asgn_col_pid;

    @FXML
    private TableColumn<peripheralData, String> asgn_col_pbrand;

    @FXML
    private TableColumn<peripheralData, String> asgn_col_modelnum;

    @FXML
    private TableColumn<peripheralData, String> asgn_col_ptype;

    @FXML
    private TableColumn<peripheralData, String> asgn_col_availability;
    
        @FXML
    private Label username;
      
        private Connection connect;
        private PreparedStatement prepare;
        private ResultSet result;
        private Statement statement;
        
        private Image image;
        
        
        public void availablePeripheralAdd(){
            String sql = "INSERT INTO peripheral02(P_id, brand,	modelnum, type, Availability, image)" + 
                    "VALUES(?,?,?,?,?,?)";
            connect = database.connectDb();
            
            try{
                Alert alert;
                
                        if(available_id.getText().isEmpty() 
                        || available_Brand.getText().isEmpty()
                        || available_Model.getText().isEmpty()
                        || available_Type.getSelectionModel().getSelectedItem() == null
                        || available_availablity.getSelectionModel().getSelectedItem() == null
                        || getData.path == null || getData.path ==""){
                     alert = new Alert(AlertType.ERROR); 
                     alert.setTitle("ERROR Message");
                     alert.setHeaderText(null);
                     alert.setContentText("Please fill all fields");
                     alert.showAndWait();
                }else{
                
            
                
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, available_id.getText());
                prepare.setString(2,available_Brand.getText());
                prepare.setString(3, available_Model.getText());
                prepare.setString(4, (String)available_Type.getSelectionModel().getSelectedItem());
                prepare.setString(5, (String)available_availablity.getSelectionModel().getSelectedItem());
                
                String uri = getData.path;
                uri = uri.replace("\\", "\\\\");
                
                
                prepare.setString(6, uri);
                
                prepare.executeUpdate();
                
                
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();
                
                availableCarShowListData();
                availablePClear();
                
                
               
                
                
            }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
            
        
            public void availablePUpdate(){
                String uri = getData.path;
                uri = uri.replace("\\", "\\\\");
                
                
                String sql = "UPDATE peripheral02 SET brand = '" + available_Brand.getText()+ "', modelnum = '"
                        + available_Model.getText() + "', type = '"
                        + available_Type.getSelectionModel().getSelectedItem() + "', Availability = '"
                        + available_availablity.getSelectionModel().getSelectedItem() + "', image  = '" + uri
                        + "' WHERE P_id = '" + available_id.getText() + "'";
                
                connect = database.connectDb();
                
                try{
                    Alert alert;
                     if(available_id.getText().isEmpty() 
                        || available_Brand.getText().isEmpty()
                        || available_Model.getText().isEmpty()
                        || available_Type.getSelectionModel().getSelectedItem() == null
                        || available_availablity.getSelectionModel().getSelectedItem() == null
                        || getData.path == null || getData.path == ""){
                     alert = new Alert(AlertType.ERROR); 
                     alert.setTitle("ERROR Message");
                     alert.setHeaderText(null);
                     alert.setContentText("Please fill all fields");
                     alert.showAndWait();
                }else{
                       alert = new Alert(AlertType.CONFIRMATION);
                       alert.setTitle("Confirmation Message");
                       alert.setHeaderText(null);
                       alert.setContentText("Are you sure in updating the Peripheral ID: " + available_id.getText() + "?" );
                       Optional<ButtonType> option = alert.showAndWait();
                       
                       
                       if(option.get().equals(ButtonType.OK)){
                          statement = connect.createStatement();
                          statement.executeUpdate(sql);
                          
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated !");
                        alert.showAndWait();

                           availableCarShowListData();
                           availablePClear();
                           
                       }
                       statement = connect.createStatement();
                     }
                }catch(Exception e){e.printStackTrace();}
            }
            
            
            
            public void availablePDelete(){
               String sql = "DELETE FROM peripheral02 WHERE P_id = '"+available_id.getText()+"'";
               
               connect = database.connectDb();
               try{
                     Alert alert;
                     if(available_id.getText().isEmpty() 
                        || available_Brand.getText().isEmpty()
                        || available_Model.getText().isEmpty()
                        || getData.path == null || getData.path == ""){
                     alert = new Alert(AlertType.ERROR); 
                     alert.setTitle("ERROR Message");
                     alert.setHeaderText(null);
                     alert.setContentText("Please fill all fields");
                     alert.showAndWait();
                }else{
                       alert = new Alert(AlertType.CONFIRMATION);
                       alert.setTitle("Confirmation Message");
                       alert.setHeaderText(null);
                       alert.setContentText("Are you sure in deleting the Peripheral ID: " + available_id.getText() + "?" );
                       Optional<ButtonType> option = alert.showAndWait();
                       
                       
                       if(option.get().equals(ButtonType.OK)){
                          statement = connect.createStatement();
                          statement.executeUpdate(sql);
                          
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();

                           availableCarShowListData();
                           availablePClear();
                           
                       }
                       statement = connect.createStatement();
                     }
                   
               }catch(Exception e){
                   e.printStackTrace();
               }
            }
            
            public void availablePClear(){
                 available_id.setText("");
                 available_Brand.setText("");
                 available_Model.setText("");
                 available_Type.getSelectionModel().clearSelection();
                 available_availablity.getSelectionModel().clearSelection();
                 getData.path = "";
                 
                 available_ImageView.setImage(null);
            }
            
            
            
        private String[] listType = {"INPUT", "OUTPUT","INPUT/OUTPUT"};
        public void availablePeripheralTypeList(){
            List<String> listT = new ArrayList<>();
            for(String data: listType){
                listT.add(data);
            }
            ObservableList listData = FXCollections.observableArrayList(listT);
            available_Type.setItems(listData); 
            
        }
        
        private String[] listStatus = {"Available", "Not Available"};
        
        
        public void availablePeripheralAvailabilityList(){
            List<String> listS = new ArrayList<>();
            for(String data: listStatus){
                listS.add(data);
            }
            ObservableList listData = FXCollections.observableArrayList(listS);
            available_availablity.setItems(listData); 
            
        }
        
        public void availablePeripheralImportImage(){
            FileChooser open = new FileChooser();
            open.setTitle("Open Image File");
            open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));
            
            File file = open.showOpenDialog(main_form.getScene().getWindow());
            
            if(file !=null){
                
                getData.path = file.getAbsolutePath();
                image = new Image(file.toURI().toString(),  233, 150, false, true);
                available_ImageView.setImage(image);
            
        }
        }
        
        public ObservableList<peripheralData> availablePeripheralListData(){
             
            ObservableList<peripheralData> listData = FXCollections.observableArrayList();
            
            String sql = "SELECT * FROM peripheral02";
                    
                    connect = database.connectDb();
                    
                    
                    try{
                        
                        prepare = connect.prepareStatement(sql);
                        result = prepare.executeQuery();
                        
                        peripheralData pd;
                        
                        while(result.next()){
                            pd = new peripheralData(result.getInt("P_id"),
                                    result.getString("brand"), 
                                    result.getString("modelnum"), 
                                    result.getString("type"),
                                    result.getString("availability"),
                                    result.getString("image"));
                            
                            listData.add(pd);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        
                    }return listData;
                    
        }
        
        
        
          private ObservableList<peripheralData> availablePList;
          public void availableCarShowListData(){
               availablePList = availablePeripheralListData();
               
               available_col_id.setCellValueFactory(new PropertyValueFactory<>("pid"));
               available_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
               available_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
               available_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
               available_col_availability.setCellValueFactory(new PropertyValueFactory<>("availability"));
       
               available_tableView.setItems(availablePList);
          }
          
          
          public void availablePSearch(){
              FilteredList<peripheralData> filter = new FilteredList<>(availablePList, e -> true);
              
              available_searchB.textProperty().addListener((Observable, oldValue, newValue) ->{
                  
                  filter.setPredicate(predicatePData ->{
                  
                      if(newValue == null || newValue.isEmpty()){
                          
                        return true;
                      }
                      
                      String searchKey = newValue.toLowerCase();
                      
                      if(predicatePData.getPid().toString().contains(searchKey)){
                          return true;
                      }else if(predicatePData.getBrand().toLowerCase().contains(searchKey)){
                          return true;
                      }else if(predicatePData.getModel().toLowerCase().contains(searchKey)){
                          return true;
                      }else if(predicatePData.getType().toLowerCase().contains(searchKey)){
                          return true;
                      }else if(predicatePData.getAvailability().toLowerCase().contains(searchKey)){
                          return true;
                      }else return false;
                 });
              }); 
              
              SortedList<peripheralData> sortList = new SortedList<>(filter);
              sortList.comparatorProperty().bind(available_tableView.comparatorProperty());
              available_tableView.setItems(sortList);
              
          }
          
          public void availablePeripheralSelect(){
             
              peripheralData pd = available_tableView.getSelectionModel().getSelectedItem();
              int num = available_tableView.getSelectionModel().getSelectedIndex();
              
              
              if((num -1) < - 1){
                  return;
              }
              available_id.setText(String.valueOf(pd.getPid()));
              available_Brand.setText(pd.getBrand());
              available_Model.setText(pd.getModel());
              
              
            
              
              
              
              
              getData.path = pd.getImage();
              
              
              String uri = "file:" + pd.getImage();
              
              image = new Image(uri, 233, 150, false, true);
              available_ImageView.setImage(image);
              
           }
          
          public ObservableList<peripheralData> assignedPListData(){
              
              ObservableList<peripheralData> listData = FXCollections.observableArrayList();
              
              String sql = "SELECT * FROM peripheral02";
              
              connect = database.connectDb();
              
              try{
                  prepare = connect.prepareStatement(sql);
                  result = prepare.executeQuery();
                  
                  peripheralData pd;
                  
                  while(result.next()){
                      pd = new peripheralData(result.getInt("P_id"), 
                              result.getString("brand"),
                              result.getString("modelnum"),
                              result.getString("type"),
                              result.getString("Availability"),
                              result.getString("image")); 
                      
                      listData.add(pd);
              }
                  
              }catch(Exception e){
                  e.printStackTrace();
              }
              return listData;
          }
          
          
          public void assignPrplPID(){
              String sql = "SELECT P_id FROM peripheral02 WHERE availability = 'Available'";
              
              connect = database.connectDb();
              
              try{
                  prepare = connect.prepareStatement(sql);
                  result = prepare.executeQuery();
                  
                  ObservableList listData = FXCollections.observableArrayList();
                  
                  while(result.next()){
                      listData.add(result.getString("P_id"));
                  }
                  asgn_Id.setItems(listData);
                  
                  
                  assignPBrand();
                  
              }catch(Exception e){
                  e.printStackTrace();
              }
          }
          
          
          public void assignPBrand(){
              String sql = "SELECT * FROM peripheral02 WHERE P_id = '"
                      +asgn_Id.getSelectionModel().getSelectedItem()+"'";
              
              connect  = database.connectDb();
              
              try{
                  prepare = connect.prepareStatement(sql);
                  result = prepare.executeQuery();
                  
                    ObservableList listData = FXCollections.observableArrayList();
                  
                  while(result.next()){
                      listData.add(result.getString("brand"));
                  }
                  asgn_Brand.setItems(listData);
                  
                  assignPModel();
                  
              }catch(Exception e){
                  e.printStackTrace();
              }
          }
          
          public void assignToEmp(){    
              EmpAssignedID();
              
              String sql = "INSERT INTO employee(emp_id, firstname,"
                      + "lastname, P_id, brand, modelnum) "
                      + "VALUES(?,?,?,?,?,?)";
              
              connect = database.connectDb();
              
              try{
                  Alert alert;
                  
                  if(asgn_FirstName.getText().isEmpty()
                          || asgn_lastName.getText().isEmpty()
                          || asgn_Id.getSelectionModel().getSelectedItem() == null
                          || asgn_Brand.getSelectionModel().getSelectedItem() == null
                          || asgn_Model.getSelectionModel().getSelectedItem() == null){
                          
                      alert = new Alert(AlertType.ERROR);
                      alert.setHeaderText(null);
                      alert.setContentText("Something wrong :3");
                      alert.showAndWait(); 
                  }else{
                      alert = new Alert(AlertType.CONFIRMATION);
                      alert.setHeaderText(null);
                      alert.setContentText("Perform Operation?");
                      Optional<ButtonType> option = alert.showAndWait();
                      
                      if(option.get().equals(ButtonType.OK)){
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1, String.valueOf(employeeId));
                        prepare.setString(2, asgn_FirstName.getText());
                        prepare.setString(3, asgn_lastName.getText());
                        prepare.setString(4, (String)asgn_Id.getSelectionModel().getSelectedItem());
                        prepare.setString(5, (String)asgn_Brand.getSelectionModel().getSelectedItem());
                        prepare.setString(6, (String)asgn_Model.getSelectionModel().getSelectedItem());
                        
                        
                        prepare.executeUpdate();
                        
                               
                        String updateP = "UPDATE peripheral02 SET availability = 'Not Available' Where P_id = '"
                                +(String)asgn_Id.getSelectionModel().getSelectedItem()+"'";
                        
                        statement = connect.createStatement();
                        statement.executeUpdate(updateP);
                 
                        
                      alert = new Alert(AlertType.INFORMATION);
                      alert.setHeaderText(null);
                      alert.setContentText("Operation Successful");
                      alert.showAndWait(); 
                      
                      
                      availablePeripheralListData();
                      assignClear();
                      assignPShowListData();
                      assignPrplPID();
                      assignPBrand();
                      assignPModel();
                      }
                        
                  }
              }catch(Exception e){
                  e.printStackTrace();}
              
              
          }
          
          
          public void assignClear(){
              asgn_FirstName.setText("");
               asgn_lastName.setText("");
               asgn_Id.getSelectionModel().clearSelection();
               asgn_Brand.getSelectionModel().clearSelection();
               asgn_Model.getSelectionModel().clearSelection();       
                       
              
          }
     
          private int employeeId;
          public void EmpAssignedID(){
              String sql = "SELECT id From employee";
              
              connect = database.connectDb();
              
              try{
                  prepare = connect.prepareStatement(sql);
                  result = prepare.executeQuery();
                  
                  while(result.next()){
                      employeeId = result.getInt("id") + 1;
                  }
              }catch(Exception e){
                  e.printStackTrace();
              }
              
              
          }
          
          
     
          
            public void assignPModel(){
              String sql = "SELECT * FROM peripheral02 WHERE brand = '"
                      +asgn_Brand.getSelectionModel().getSelectedItem()+"'";
              
              connect  = database.connectDb();
              
              try{
                  prepare = connect.prepareStatement(sql);
                  result = prepare.executeQuery();
                  
                    ObservableList listData = FXCollections.observableArrayList();
                  
                  while(result.next()){
                      listData.add(result.getString("modelnum"));
                  }
                  asgn_Model.setItems(listData);
                  
                  
                  
              }catch(Exception e){
                  e.printStackTrace();
              }
          }
          
          private ObservableList<peripheralData> assignedPList;
          public void assignPShowListData(){
              
              assignedPList = assignedPListData();
              
              asgn_col_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
              asgn_col_pbrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
              asgn_col_modelnum.setCellValueFactory(new PropertyValueFactory<>("model"));
              asgn_col_ptype.setCellValueFactory(new PropertyValueFactory<>("type"));
              asgn_col_availability.setCellValueFactory(new PropertyValueFactory<>("Availability"));
              
              asgn_tableView.setItems(assignedPList);
        }
              
    
          public void displayUserName(){
        String user = getData.username;
        
        username.setText(user.substring(0,1).toUpperCase() + user.substring(1));

        
    }
    public void close(){
        System.exit(0);
    }
    
    public void swicthForm(ActionEvent event){
        if(event.getSource() == home_btn){
            home_form.setVisible(true);
            available_form.setVisible(false);
            assign_form.setVisible(false);
            
        }else if(event.getSource() == available_btn){
            home_form.setVisible(false);
            available_form.setVisible(true);
            assign_form.setVisible(false);
            
            
                assignPrplPID();
            assignPShowListData();  
            availablePeripheralListData();
            availablePeripheralAvailabilityList();
            availablePeripheralTypeList();
            availablePSearch();
         
          
           
        
        }else if(event.getSource()== assign_btn){
            home_form.setVisible(false);
            available_form.setVisible(false);
            assign_form.setVisible(true);
            
            
            assignPShowListData();
            assignPrplPID();
            assignPBrand();
            assignPModel();
        }
    }
    
     
    private double x = 0;
    private double y = 0;
    
    public void logout(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        
        try{
            if(option.get().equals(ButtonType.OK)){
                signout_btn.getScene().getWindow().hide();
                
             Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
             Stage stage = new Stage(); 
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/Style.css");
        
        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
            
        });
        
        root.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            
            stage.setOpacity(.8);
        });
        
        root.setOnMouseReleased((MouseEvent event) ->{
            stage.setOpacity(1);
        });
        
        stage.initStyle(StageStyle.TRANSPARENT); 
        stage.setScene(scene);
        stage.show();
            
        }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUserName();
        availablePeripheralListData();
        availablePeripheralAvailabilityList();
        availablePeripheralTypeList();
        availableCarShowListData();
        availablePSearch();
        assignPShowListData();
        assignPrplPID();
        assignPBrand();
        assignPModel();
    }
    
}
