package pl.polsl.table;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import pl.polsl.controllers.AllItemsController;
import pl.polsl.model.ModelFx;

/**
 * Main class, calls MenuFxmlController
 * @author sebastianc01
 */
public class App extends Application {

    private static Scene scene;

    private static ModelFx model;
    /**
     * Start method
     * @param stage existing scene
     * @throws IOException called fxml might not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/pl/polsl/views/menuFxml.fxml"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * It sets root of the scene
     * @param fxml string with the name of fxml and path
     * @throws IOException called fxml may not exist
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        //fxmlLoader.setControllerFactory( p -> { return new AllItemsController(model);} );        
        return fxmlLoader.load();
    }
    
    /**
     * Main method
     * @param args arguments passed in the command line
     */
    public static void main(String[] args) {
        model = new ModelFx();
        launch();
    }

}