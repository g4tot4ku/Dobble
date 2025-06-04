package dobblefxml;

import dobblefxml.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static javafx.application.Application.launch;

/**
 * Clase Principal del juego Dobble Geek
 */
public class DobbleFXML extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("FXML.fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("No se encontró el archivo FXML.fxml en el paquete dobblefxml");
        }
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        FXMLController controller = loader.getController();

        List<Symbol> symbolPool = createSymbolPool();
        List<Card> deck = createDeck(symbolPool);

        // Verificamos la existencia de dos cartas 
        if (deck.size() < 2) {
            throw new IllegalStateException("El mazo debe tener al menos 2 cartas.");
        }

        
        controller.initGame(stage, deck);

        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Dobble Geek");
        stage.show();
    }
    // Lista con todos los símbolos que vamos a usar
    private List<Symbol> createSymbolPool() {
        List<Symbol> symbols = new ArrayList<>();
        String[] iconNames = {"alien.png", "brain.png", "joystick.png", "planet.png", "superhero.png", 
                             "sword.png", "web.png", "wizard.png", "zombie.png"};
        String[] emojis = {"👾", "🧠", "🕹️", "🪐", "🦸", "⚔️", "🕸️", "🧙", "🧟"};

        //Recorremos los nombres e imágenes para crear cada simbolo 
        for (int i = 0; i < iconNames.length; i++) {
            URL resource = getClass().getResource("/resources/" + iconNames[i]);
            if (resource == null) {
                throw new IllegalStateException("No se encontró el recurso: /resources/" + iconNames[i]);
            }
            
            Image image = new Image(resource.toExternalForm());
            if (image.isError()) {
                throw new IllegalStateException("Error al cargar la imagen: " + iconNames[i]);
            }
            symbols.add(new Symbol(emojis[i], image));
        }
        return symbols;
    }

    //Mazo de cartas usando los simbolos 
    private List<Card> createDeck(List<Symbol> symbolPool) {
        if (symbolPool == null || symbolPool.isEmpty()) {
            throw new IllegalStateException("El pool de símbolos está vacío. No se pueden crear cartas.");
        }
        if (symbolPool.size() < 9) { 
            throw new IllegalStateException("Se necesitan al menos 9 símbolos para crear el mazo.");
        }

        //Lista vacia para guardar las cartas
        List<Card> deck = new ArrayList<>();

        // Generamos un mazo manualmente con 5 cartas, cada una con 4 símbolos
        // Símbolos: 0=alien, 1=brain, 2=joystick, 3=planet, 4=superhero, 5=sword, 6=web, 7=wizard, 8=zombie
        // Carta 1: 0, 1, 2, 3
        List<Symbol> symbols1 = new ArrayList<>();
        symbols1.add(symbolPool.get(0));
        symbols1.add(symbolPool.get(1));
        symbols1.add(symbolPool.get(2));
        symbols1.add(symbolPool.get(3));
        deck.add(new Card(symbols1));

        
        List<Symbol> symbols2 = new ArrayList<>();
        symbols2.add(symbolPool.get(0));
        symbols2.add(symbolPool.get(4));
        symbols2.add(symbolPool.get(5));
        symbols2.add(symbolPool.get(6));
        deck.add(new Card(symbols2));

        
        List<Symbol> symbols3 = new ArrayList<>();
        symbols3.add(symbolPool.get(1));
        symbols3.add(symbolPool.get(4));
        symbols3.add(symbolPool.get(7));
        symbols3.add(symbolPool.get(8));
        deck.add(new Card(symbols3));

       
        List<Symbol> symbols4 = new ArrayList<>();
        symbols4.add(symbolPool.get(2));
        symbols4.add(symbolPool.get(5));
        symbols4.add(symbolPool.get(7));
        symbols4.add(symbolPool.get(3));
        deck.add(new Card(symbols4));

        
        List<Symbol> symbols5 = new ArrayList<>();
        symbols5.add(symbolPool.get(3));
        symbols5.add(symbolPool.get(6));
        symbols5.add(symbolPool.get(8));
        symbols5.add(symbolPool.get(0));
        deck.add(new Card(symbols5));

        return deck;
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}