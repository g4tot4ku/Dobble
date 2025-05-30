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

        // Crear botones para los símbolos de la primera carta
        for (Symbol symbol : cardA.getSymbols()) {
            Button button = new Button();
            ImageView imageView = new ImageView(symbol.getImage());
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            button.setGraphic(imageView);
            button.setOnAction(event -> handleSymbolSelection(symbol));
            cardPaneA.getChildren().add(button);
            symbolButtons.add(button);
        }

        // Crear botones para los símbolos de la segunda carta
        for (Symbol symbol : cardB.getSymbols()) {
            Button button = new Button();
            ImageView imageView = new ImageView(symbol.getImage());
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            button.setGraphic(imageView);
            button.setOnAction(event -> handleSymbolSelection(symbol));
            cardPaneB.getChildren().add(button);
            symbolButtons.add(button);
        }

        scoreLabel.setText("Puntaje: " + score);
        nextRoundButton.setDisable(true);
        feedbackLabel.setText("🎮 ¡Encuentra el símbolo común!");
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