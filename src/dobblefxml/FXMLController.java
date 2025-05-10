/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dobblefxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import dobblegeek.model.*;
import dobblegeek.view.GameView;

/**
 * Controlador principal del juego que maneja la interacción entre la lógica (GameLogic) 
 * y la vista (GameView). Su objetivo es coordinar las acciones del usuario y actualizar 
 * el estado del juego.
 */
public class GameController {
    // Referencias a la lógica del juego y la interfaz gráfica
    private GameLogic logic;  // Maneja las reglas y el estado del juego
    private GameView view;    // Encargada de mostrar la interfaz al usuario

    /**
     * Constructor del controlador.
     * @param logic Instancia de GameLogic (lógica del juego).
     * @param view Instancia de GameView (interfaz gráfica).
     */
    public GameController(GameLogic logic, GameView view) {
        this.logic = logic;
        this.view = view;
        setup();  // Configuración inicial del juego
    }

    /**
     * Inicializa la vista con las primeras cartas del juego.
     * - Obtiene las cartas actuales de la lógica.
     * - Las muestra en la vista junto con un manejador de clics.
     */
    private void setup() {
        view.showCards(
            logic.getCardA(),       // Primera carta a mostrar
            logic.getCardB(),       // Segunda carta a mostrar
            this::onSymbolClick    // Método que se ejecutará al hacer clic en un símbolo
        );
    }

    /**
     * Método llamado cuando el usuario hace clic en un símbolo.
     * @param selected Símbolo seleccionado por el jugador.
     */
    public void onSymbolClick(Symbol selected) {
        // Verifica si el símbolo seleccionado está presente en AMBAS cartas
        boolean isCorrect = logic.getCardB().getSymbols().contains(selected) 
                        && logic.getCardA().getSymbols().contains(selected);

        // Muestra retroalimentación visual (ej: "¡Correcto!" o "Incorrecto")
        view.showFeedback(isCorrect);

        // Si la selección fue correcta:
        if (isCorrect) {
            logic.nextRound();  // Avanza a la siguiente ronda (actualiza las cartas)
            
            // Muestra las nuevas cartas en la vista
            view.showCards(
                logic.getCardA(), 
                logic.getCardB(), 
                this::onSymbolClick  // Reutiliza el mismo manejador de clics
            );
        }
    }
}