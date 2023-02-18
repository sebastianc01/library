/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl.controllers;

import pl.polsl.exceptions.CorrectDate;
import pl.polsl.exceptions.CorrectInput;
import pl.polsl.exceptions.IncorrectDateException;
import pl.polsl.exceptions.ForbiddenCharactersException;
import java.io.IOException;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import pl.polsl.model.*;
import pl.polsl.table.App;
//import pl.polsl.model.Model;
/**
 * FXML Controller class, it shows all items.
 *
 * @author sebastianc01
 */
public class AllItemsController {


    @FXML
    private TableView table;
    @FXML
    private TableColumn nameTableColumn;
    @FXML
    private TableColumn authorTableColumn;
    @FXML
    private TableColumn dateTableColumn;
    @FXML
    private TableColumn<ItemFx, Number> currentAmountTableColumn;
    @FXML
    private TableColumn<ItemFx, Number> totalAmountTableColumn;
    @FXML
    private Button backToMenuButton;
    @FXML
    private Button addItemButton;
    @FXML
    private Button removeItemButton;
    
    private final ObservableList<ItemFx> data;    
    private final ModelFx model;
    
    /**
     * Constructor
     * @param model existing model
     */
    public AllItemsController(ModelFx model) {
        this.model = model;
        try {
        model.loadFileToData("database.txt");
        } catch(Exception e) {
            showError(e.getMessage());
        }
        data = FXCollections.observableArrayList(model.getDataFx());
        data.addListener(new ListChangeListener<ItemFx>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends ItemFx> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            //System.out.println("zamiana");
                        }
                    } else if (change.wasUpdated()) {
                        //System.out.println("uaktualnienie");
                    } else {
                        for (var remitem : change.getRemoved()) {
                            model.getDataFx().remove(remitem);
                        }
                        for (var additem : change.getAddedSubList()) {
                            model.getDataFx().add(additem);
                        }
                    }
                }
            }
        });
    }   
    
    /**
     * Initializes the controller class.
     */  
    @FXML
    public void initialize() {
        table.setItems(data);
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<ItemFx, String>("name"));
        authorTableColumn.setCellValueFactory(new PropertyValueFactory<ItemFx, String>("author"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<ItemFx, String>("date"));
        currentAmountTableColumn.setCellValueFactory(cellData -> cellData.getValue().currentAmountProperty());
        totalAmountTableColumn.setCellValueFactory(cellData -> cellData.getValue().totalAmountProperty());
        table.setEditable(true);
        nameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());	

        nameTableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemFx, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemFx, String> t) {
                try{
                ((ItemFx) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(CorrectInput.loadCorrectInput(t.getNewValue()));
                lookForTheSameItems();
                } catch(ForbiddenCharactersException e) {
                    showError(e.getMessage());
                    table.refresh();
                }
            }
        });
        authorTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorTableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemFx, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemFx, String> t) {
                try{
                ((ItemFx) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuthor(CorrectInput.loadCorrectInput(t.getNewValue()));
                lookForTheSameItems();
                } catch(ForbiddenCharactersException e) {
                    showError(e.getMessage());
                    table.refresh();
                }
            }
        });
        dateTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateTableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemFx, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemFx, String> t) {
                try{
                ((ItemFx) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDate(CorrectInput.loadCorrectInput(CorrectDate.loadCorrectDate(t.getNewValue())));
                lookForTheSameItems();
                } catch(IncorrectDateException | ForbiddenCharactersException e) {
                    showError(e.getMessage());
                    table.refresh();
                }
            }
        });
        currentAmountTableColumn.setCellFactory(col -> new IntegerEditingCell());
        totalAmountTableColumn.setCellFactory(col -> new IntegerEditingCellTotalAmount());
    }
    
    /**
     * Method called when user wants to add a new item.
     * @param evenr mouse click
     */
    @FXML
    private void addItemButton(ActionEvent evenr) {
        data.add(new ItemFx("","", "", 1, 1));
    }

    /**
     * Method called when user wants to remove existing item.
     * @param evenr mouse click
     */
    @FXML
    private void removeItemButton(ActionEvent evenr) {
        int index = table.getSelectionModel().getSelectedIndex();
        if(index != -1) data.remove(index);
    }
    
    /**
     * Method called when user wants to return to the main menu.
     * @param evenr mouse click
     */
    @FXML
    private void backToMainMenu(ActionEvent evenr) {
        try{
            model.saveDataToFile("database.txt");
        } catch(IOException e) {
            showError("Dane nie zostaly zapisane");
        }
        try{
        Stage stage = (Stage) backToMenuButton.getScene().getWindow();
        stage.close();
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
     * Method search for the same items and tries to find a duplicate. When it finds a few the same elements, then sums currentAmount and totalAmount and removes all duplicates.
     */
    private void lookForTheSameItems() {
        for(int i=0; i < model.getDataFx().size(); ++i) {
            for(int k = i + 1; k < model.getDataFx().size(); ++k) {
                if(model.getDataFx().get(i).equals(model.getDataFx().get(k))) {
                    data.get(i).setCurrentAmount(data.get(i).getCurrentAmount() + data.get(k).getCurrentAmount());
                    data.get(i).setTotalAmount(data.get(i).getTotalAmount() + data.get(k).getTotalAmount());
                    data.remove(k);
                    //table.refresh();
                }
            }
        }
    }
    
    /**
     * Method used to report unwanted states and communicate with the user.
     * @param message string value, message displayed to the user.
     */
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait();
    }
    
    /**
     * Class responsible for correct editing currentAmount value.
     */
    public class IntegerEditingCell extends TableCell<ItemFx, Number> {

        private final TextField textField = new TextField();
        private final Pattern intPattern = Pattern.compile("-?\\d+");

        /**
         * Constructor
         */
        public IntegerEditingCell() {
            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (! isNowFocused) {
                    processEdit();
                }
            });
            textField.setOnAction(event -> processEdit());
        }

        /**
         * Checks value, if it is correct then commits edit, otherwise cancels it.
         */
        private void processEdit() {
            String text = textField.getText();
            if (intPattern.matcher(text).matches()) {
                commitEdit(Integer.parseInt(text));
            } else {
                cancelEdit();
            }
        }

        /**
         * Updates an item
         * @param value new value
         * @param empty when empty contains true, false otherwise
         */
        @Override
        public void updateItem(Number value, boolean empty) {
            super.updateItem(value, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (isEditing()) {
                setText(null);
                textField.setText(value.toString());
                setGraphic(textField);
            } else {
                setText(value.toString());
                setGraphic(null);
            }
        }
        
        /**
         * Allows to input data.
         */
        @Override
        public void startEdit() {
            super.startEdit();
            Number value = getItem();
            if (value != null) {
                textField.setText(value.toString());
                setGraphic(textField);
                setText(null);
            }
        }

        /**
         * Data incorrect, returns previous data.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().toString());
            setGraphic(null);
        }

        /**
         * Data correct, commits edited data.
         * @param value new value
         */
        @Override
        public void commitEdit(Number value) {
            if(value.intValue() <= this.getTableRow().getItem().getTotalAmount()) {
                super.commitEdit(value);
                ((ItemFx)this.getTableRow().getItem()).setCurrentAmount(value.intValue());
            }
        }
    }
    
    /**
     * Class responsible for correct editing currentAmount value.
     */
    public class IntegerEditingCellTotalAmount extends TableCell<ItemFx, Number> {
        private final TextField textField = new TextField();
        private final Pattern intPattern = Pattern.compile("-?\\d+");

        /**
         * Constructor
         */
        public IntegerEditingCellTotalAmount() {
            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (! isNowFocused) {
                    processEdit();
                }
            });
            textField.setOnAction(event -> processEdit());
        }

        /**
         * Checks value, if it is correct then commits edit, otherwise cancels it.
         */
        private void processEdit() {
            String text = textField.getText();
            if (intPattern.matcher(text).matches()) {
                commitEdit(Integer.parseInt(text));
            } else {
                cancelEdit();
            }
        }

        /**
         * Updates an item
         * @param value new value
         * @param empty when empty contains true, false otherwise
         */
        @Override
        public void updateItem(Number value, boolean empty) {
            super.updateItem(value, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (isEditing()) {
                setText(null);
                textField.setText(value.toString());
                setGraphic(textField);
            } else {
                setText(value.toString());
                setGraphic(null);
            }
        }

        /**
         * Allows to input data.
         */
        @Override
        public void startEdit() {
            super.startEdit();
            Number value = getItem();
            if (value != null) {
                textField.setText(value.toString());
                setGraphic(textField);
                setText(null);
            }
        }

        /**
         * Data incorrect, returns previous data.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().toString());
            setGraphic(null);
        }
        
        /**
         * Data correct, commits edited data.
         * @param value new value
         */
        @Override
        public void commitEdit(Number value) {
            super.commitEdit(value);
            ((ItemFx)this.getTableRow().getItem()).setTotalAmount(value.intValue());
        }
    }
}
