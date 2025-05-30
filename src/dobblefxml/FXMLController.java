package dobblefxml;

import dobblefxml.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FXMLController {
    @FXML
    private Label feedbackLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private FlowPane cardPaneA;

    @FXML
    private FlowPane cardPaneB;

    @FXML
    private Button nextRoundButton;

    private GameLogic gameLogic;
    private Stage stage;
    private int score;
    private Set<Button> symbolButtons = new HashSet<>();
    private Symbol correctSymbol;

    public void initGame(Stage stage, List<Card> deck) {
        this.stage = stage;
        this.gameLogic = new GameLogic(deck);
        this.score = 0;
        updateView();
    }

    private void updateView() {
        Card cardA = gameLogic.getCardA();
        Card cardB = gameLogic.getCardB();

        // Limpiar los paneles
        cardPaneA.getChildren().clear();
        cardPaneB.getChildren().clear();
        symbolButtons.clear();

        // Encontrar el símbolo común
        correctSymbol = null;
        for (Symbol s : cardA.getSymbols()) {
            if (cardB.getSymbols().contains(s)) {
                correctSymbol = s;
                break;
            }
        }

        // Distribuir los símbolos de la primera carta en un círculo
        distributeSymbolsCircularly(cardA, cardPaneA);

        // Distribuir los símbolos de la segunda carta en un círculo
        distributeSymbolsCircularly(cardB, cardPaneB);

        scoreLabel.setText("Puntaje: " + score);
        nextRoundButton.setDisable(true);
        feedbackLabel.setText("🎮 ¡Encuentra el símbolo común!");
    }

    private void distributeSymbolsCircularly(Card card, FlowPane pane) {
        List<Symbol> symbols = card.getSymbols();
        int numSymbols = symbols.size();
        double centerX = 100; // Centro del círculo (mitad del ancho de cardPane: 200/2)
        double centerY = 100; // Centro del círculo (mitad del alto de cardPane: 200/2)
        double radius = 60;   // Radio del círculo donde se posicionarán los botones

        for (int i = 0; i < numSymbols; i++) {
            Symbol symbol = symbols.get(i);

            // Calcular la posición del botón en un círculo
            double angle = 2 * Math.PI * i / numSymbols; // Ángulo para cada símbolo
            double x = centerX + radius * Math.cos(angle) - 30; // -30 para centrar el botón (ancho/2)
            double y = centerY + radius * Math.sin(angle) - 30; // -30 para centrar el botón (alto/2)

            Button button = new Button();
            ImageView imageView = new ImageView(symbol.getImage());
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            button.setGraphic(imageView);
            button.setStyle("-fx-background-color: transparent;"); // Hacer el fondo del botón transparente
            button.setOnAction(event -> handleSymbolSelection(symbol));

            // Posicionar el botón
            button.setLayoutX(x);
            button.setLayoutY(y);

            pane.getChildren().add(button);
            symbolButtons.add(button);
        }
    }

    private void handleSymbolSelection(Symbol selectedSymbol) {
        if (selectedSymbol.equals(correctSymbol)) {
            score++;
            feedbackLabel.setText("¡Correcto! +1 punto");
        } else {
            feedbackLabel.setText("¡Incorrecto! Intenta de nuevo");
        }
        updateButtonStates();
    }

    private void updateButtonStates() {
        for (Button button : symbolButtons) {
            button.setDisable(true);
        }
        nextRoundButton.setDisable(false);
    }

    @FXML
    private void handleNextRound() {
        gameLogic.nextRound();
        updateView();
    }
}