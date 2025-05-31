package dobblefxml;

import dobblefxml.model.*;
import javafx.animation.AnimationTimer;
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
    private Label timerLabel;
    @FXML
    private Label playerLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private FlowPane cardPaneA;
    @FXML
    private FlowPane cardPaneB;
    @FXML
    private Button nextRoundButton;
    @FXML
    private Button endGameButton;
    @FXML
    private Button restartButton;

    private GameLogic gameLogic;
    private Stage stage;
    private int score;
    private Set<Button> symbolButtons = new HashSet<>();
    private Symbol correctSymbol;
    private AnimationTimer timer;
    private long lastUpdate = 0;
    private int timeLeft = 10; // 1 minutos en segundos
    private int currentPlayer = 1; // 1 para Jugador 1, 2 para Jugador 2
    private int player1Score = 0;
    private int player2Score = 0;
    private int player1TimeLeft = 0;
    private int player2TimeLeft = 0;
    private List<Card> deck;

    public void initGame(Stage stage, List<Card> deck) {
        this.stage = stage;
        this.deck = deck;
        this.gameLogic = new GameLogic(deck);
        this.score = 0;
        startTimer();
        updateView();
    }

    private void startTimer() {
        timeLeft = 10;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                }
                // Actualizar cada 2 segundos (2 mil millones de nanosegundos)
                if (now - lastUpdate >= 2_000_000_000L) {
                    if (timeLeft > 0) {
                        timeLeft--;
                        timerLabel.setText("Tiempo: " + timeLeft + "s");
                    } else {
                        endTurn();
                        this.stop();
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    private void updateView() {
        Card cardA = gameLogic.getCardA();
        Card cardB = gameLogic.getCardB();

        cardPaneA.getChildren().clear();
        cardPaneB.getChildren().clear();
        symbolButtons.clear();

        correctSymbol = null;
        for (Symbol s : cardA.getSymbols()) {
            if (cardB.getSymbols().contains(s)) {
                correctSymbol = s;
                break;
            }
        }

        distributeSymbolsCircularly(cardA, cardPaneA);
        distributeSymbolsCircularly(cardB, cardPaneB);

        scoreLabel.setText("Puntaje: " + score);
        nextRoundButton.setDisable(true);
        playerLabel.setText("Turno: Jugador " + currentPlayer);
        feedbackLabel.setText("🎮 ¡Encuentra el símbolo común!");
        restartButton.setVisible(false);
    }

    private void distributeSymbolsCircularly(Card card, FlowPane pane) {
        List<Symbol> symbols = card.getSymbols();
        int numSymbols = symbols.size();
        double centerX = 100;
        double centerY = 100;
        double radius = 60;

        for (int i = 0; i < numSymbols; i++) {
            Symbol symbol = symbols.get(i);
            double angle = 2 * Math.PI * i / numSymbols;
            double x = centerX + radius * Math.cos(angle) - 30;
            double y = centerY + radius * Math.sin(angle) - 30;

            Button button = new Button();
            ImageView imageView = new ImageView(symbol.getImage());
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            button.setGraphic(imageView);
            button.setStyle("-fx-background-color: transparent;");
            button.setOnAction(event -> handleSymbolSelection(symbol));

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

    private void endTurn() {
        if (currentPlayer == 1) {
            player1Score = score;
            player1TimeLeft = timeLeft;
            currentPlayer = 2;
            score = 0;
            startTimer();
            updateView();
        } else {
            player2Score = score;
            player2TimeLeft = timeLeft;
            showWinner();
        }
    }

    @FXML
    private void handleNextRound() {
        if (timeLeft > 0) {
            gameLogic.nextRound();
            updateView();
        }
    }

    @FXML
    private void handleEndGame() {
        timer.stop();
        endTurn();
    }

    private void showWinner() {
        String winner;
        if (player1Score > player2Score) {
            winner = "¡Jugador 1 gana con " + player1Score + " puntos!";
        } else if (player2Score > player1Score) {
            winner = "¡Jugador 2 gana con " + player2Score + " puntos!";
        } else if (player1TimeLeft > player2TimeLeft) {
            winner = "¡Empate! Gana Jugador 1 por tiempo restante (" + player1TimeLeft + "s).";
        } else if (player2TimeLeft > player1TimeLeft) {
            winner = "¡Empate! Gana Jugador 2 por tiempo restante (" + player2TimeLeft + "s).";
        } else {
            winner = "¡Empate! Ambos jugadores tienen el mismo puntaje y tiempo.";
        }
        feedbackLabel.setText(winner);
        cardPaneA.getChildren().clear();
        cardPaneB.getChildren().clear();
        nextRoundButton.setDisable(true);
        endGameButton.setDisable(true);
        restartButton.setVisible(true);
        timerLabel.setText("Tiempo: 0s");
        playerLabel.setText("");
    }

    @FXML
    private void handleRestartGame() {
        currentPlayer = 1;
        score = 0;
        player1Score = 0;
        player2Score = 0;
        player1TimeLeft = 0;
        player2TimeLeft = 0;
        gameLogic = new GameLogic(deck);
        startTimer();
        updateView();
        endGameButton.setDisable(false);
    }
}