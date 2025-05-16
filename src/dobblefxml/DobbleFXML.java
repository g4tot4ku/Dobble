package dobblefxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for Dobble game
 */
public class DobbleFXML extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));
        Parent root = loader.load();
        
        // Get the controller and set up game logic
        FXMLController controller = loader.getController();
        
        // Note: You'll need to create and populate a deck of cards here
        // and pass it to the GameLogic constructor
        // For now, we'll leave this as a placeholder
        // List<Card> deck = createDeck();
        // GameLogic gameLogic = new GameLogic(deck);
        // controller.setGameLogic(gameLogic);
        
        // Set up the scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Dobble Game");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}