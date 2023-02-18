/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import pl.polsl.model.ModelFx;
import pl.polsl.table.App;
/**
 * FXML Controller class, it controls the main menu
 *
 * @author sebastianc01
 */
public class MenuFxmlController implements Initializable {


    @FXML
    private Button allItemsButton;
    @FXML
    private Button availableItemsButton;
    @FXML
    private Button exitButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /**
     * When user clicks the "All items" button it runs a class AllItemsController.
     * @param event mouse click
     */
    @FXML
    private void AllItemsDisplay(ActionEvent event) {
        try{
        ModelFx model = new ModelFx();
        Stage stage = (Stage) allItemsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/pl/polsl/views/allItems.fxml"));
        fxmlLoader.setControllerFactory( p -> { return new AllItemsController(model);} );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        //main.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e) {
            showError("Operacja nie powiodla sie.");
        }
    }
    /**
     * When user clicks the "Available items" button it runs a class AvailableItemsController.
     * @param event mouse click
     */
    @FXML
    private void availableItemsDisplay(ActionEvent event) {
        ModelFx model = new ModelFx();
        try{
        Stage stage = (Stage) availableItemsButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/pl/polsl/views/availableItems.fxml"));
        fxmlLoader.setControllerFactory( p -> { return new AvailableItemsController(model);} );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        //main.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e) {
            showError("Operacja nie powiodla sie.");
        }
    }
    
    /**
     * It allows user to exit program.
     * @param event mouse click
     */
    @FXML
    private void exitAndSave(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
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
