/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.polsl.model.ItemFx;
import pl.polsl.model.ModelFx;
import pl.polsl.table.App;
/**
 * FXML Controller class, it shows all available items.
 *
 * @author sebastianc01
 */
public class AvailableItemsController implements Initializable {


    @FXML
    private TableView table;
    @FXML
    private TableColumn nameTableColumn;
    @FXML
    private TableColumn authorTableColumn;
    @FXML
    private TableColumn dateTableColumn;
    @FXML
    private Button backToMenuButton;
    ObservableList<ItemFx> data;
    ModelFx model;
    
    /**
     * Constructor
     * @param model existing model
     */
    public AvailableItemsController(ModelFx model) {
        this.model = model;
        try {
        model.loadFileToData("database.txt");
        } catch(Exception e) {
            showError(e.getMessage());
        }
        data = FXCollections.observableArrayList(model.getDataFx());
        for(int i=0;i<data.size();++i) {
            if(data.get(i).getCurrentAmount() <= 0) {
                data.remove(i);
                if(i>0) {
                    --i;
                }
            }
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table.setItems(data);
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<ItemFx, String>("name"));
        authorTableColumn.setCellValueFactory(new PropertyValueFactory<ItemFx, String>("author"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<ItemFx, String>("date"));
        table.setEditable(false);
    }    
    
    /**
     * Method called when user wants to return to the main menu.
     * @param evenr mouse click
     */
    @FXML
    private void backToMenu(ActionEvent event) {
        Stage stage = (Stage) backToMenuButton.getScene().getWindow();
        stage.close();
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/pl/polsl/views/menuFxml.fxml"));
        //fxmlLoader.setControllerFactory( p -> { return new AllItemsController(model);} );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        //main.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e) {
            showError("Operacja nie powiodla sie.");
        }
    }

    /**
     * Method used to report unwanted states and communicate with the user.
     * @param message string value, message displayed to the user.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait();
    }
}
