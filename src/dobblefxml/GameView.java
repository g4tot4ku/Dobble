/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dobblefxml;

import dobblefxml.model.Card;
import dobblefxml.model.Symbol;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 *
 * @author dmend_qolwfa0
 */
public class GameView {
    private Stage stage;
    private FlowPane cardPaneA;
    private FlowPane cardPaneB;
    private Label feedbackLabel;
    
    public GameView(Stage stage){
        this.stage = stage;
        setupUI();
        
    }
    
    private void setupUI(){
        cardPaneA = new FlowPane();
        cardPaneB = new FlowPane();
        feedbackLabel = new Label("Bem-vindo ao Dobble Geek!");
        
            VBox root = new VBox(10, feedbackLabel, cardPaneA, cardPaneB);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void showCards(Card a, Card b, Consumer<Symbol> clickHandler) {
        cardPaneA.getChildren().clear();
        cardPaneB.getChildren().clear();

        for (Symbol s : a.getSymbols()) {
            ImageView img = new ImageView(s.getImage());
            img.setFitHeight(60);
            img.setFitWidth(60);
            img.setOnMouseClicked(e -> clickHandler.accept(s));
            cardPaneA.getChildren().add(img);
        }

        for (Symbol s : b.getSymbols()) {
            ImageView img = new ImageView(s.getImage());
            img.setFitHeight(60);
            img.setFitWidth(60);
            img.setOnMouseClicked(e -> clickHandler.accept(s));
            cardPaneB.getChildren().add(img);
        }
    }

    public void showFeedback(boolean correct) {
        feedbackLabel.setText(correct ? "✅ Correcto!" : "❌ Intenta de nuevo.");
    }
}
