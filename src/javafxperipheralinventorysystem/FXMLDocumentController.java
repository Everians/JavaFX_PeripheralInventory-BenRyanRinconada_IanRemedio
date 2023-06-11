/*
 *ITP121 INTEGRATIVE PROGRAMMING & TECHNOLOGIES Final / Project. 
 *By: Ben Ryan Rinconada And Ian Remedio
 */
package javafxperipheralinventorysystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafxperipheralinventorysystem.getData.username;

/**
 *
 * @author benry
 */
public class FXMLDocumentController implements Initializable {
    
   @FXML
    private AnchorPane main_form;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login_btn;

    @FXML
    private Button close;
    
       @FXML
    private Label username;
       
  
    
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private double x = 0;
    private double y = 0;    
    public void LoginAdmin(){
     String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
     
     connect = database.connectDb();
     
     try{
         prepare = connect.prepareStatement(sql);
         prepare.setString(1,userName.getText());
         prepare.setString(2,password.getText());
         
         
         result = prepare.executeQuery();
          Alert alert;
          if(userName.getText().isEmpty() || password.getText().isEmpty()){
              alert = new Alert(AlertType.ERROR);
              alert.setHeaderText(null);
              alert.setContentText("Fill all Fields");
              alert.showAndWait();
          }else{
                if(result.next()){
                     
                        getData.username = userName.getText();
                        
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("LOGIN SUCESSFUL!!!");
                        alert.showAndWait();
                        login_btn.getScene().getWindow().hide();
                        
                        Parent root = FXMLLoader.load(getClass().getResource("dash.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        
                        root.setOnMousePressed((MouseEvent event)->{
                            x = event.getSceneX();
                            y = event.getSceneY();
                            });
                        
                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() -x);
                            stage.setY(event.getScreenY() -y);
                        });
                        
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();

                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username or Password");
                    alert.showAndWait();

                }
          }     
         
     }catch(Exception e){e.printStackTrace();}
     
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    public void close(){
        System.exit(0);  
    }
}
