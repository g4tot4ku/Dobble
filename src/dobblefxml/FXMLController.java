package dobblefxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import dobblefxml.model.Symbol;
import dobblefxml.model.Card;
import dobblefxml.model.GameLogic;

/**
 * Controlador principal del juego que maneja la interacción entre la lógica (GameLogic) 
 * y la vista (GameView). Su objetivo es coordinar las acciones del usuario y actualizar 
 * el estado del juego.
 */
public class FXMLController implements Initializable {
    // Referencias a la lógica del juego
    private GameLogic logic;  // Maneja las reglas y el estado del juego
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize game logic here
        // You'll need to create and populate a deck of cards first
        // For now, we'll leave this empty as the deck creation would be complex
    }
    
    /**
     * Método llamado cuando el usuario hace clic en un símbolo.
     * @param selected Símbolo seleccionado por el jugador.
     */
    public void onSymbolClick(Symbol selected) {
        if (logic != null) {
            // Verifica si el símbolo seleccionado está presente en AMBAS cartas
            boolean isCorrect = logic.getCardB().getSymbols().contains(selected) 
                            && logic.getCardA().getSymbols().contains(selected);

            // Si la selección fue correcta:
            if (isCorrect) {
                logic.nextRound();  // Avanza a la siguiente ronda (actualiza las cartas)
            }
        }
    }
    
    // Setter for game logic
    public void setGameLogic(GameLogic logic) {
        this.logic = logic;
    }
}