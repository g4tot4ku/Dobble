package dobblefxml.model;

/*Representa cada simbolo geek ( nombre + imagen ) */
import javafx.scene.image.Image;
import java.util.List;
import java.util.Random;

public class Symbol {
    private String name;
    private Image image;
    
    public Symbol(String name, Image image ){
        this.name = name;
        this.image = image;
    }
}
        public String getName () { return name; }
        public Image getImage() { return image; }
}

/*Una carta contiene varios simbolos */
public class Card {
private List<Symbol> symbols;

private Card(List<Symbol> symbols){
this.symbols = symbols;
}
public List<Symbol> getSymbols (){ return symbols;}

public boolean hasSymbol(Symbol symbol){
return symbols.contains(symbol);
}
}

/*
Maneja la logica general del juego, es decir, clase
que maneja el estado del juego, generacion de cartas, 
verificacion de coincidencias, puntajes,etc.
*/
public class GameLogic {
private List<Card> deck;
private Card currentCardA;
private Card currentCardB;

public GameLogic(List<Card> deck){
this.deck = deck;
nextRound();
}

public void nextRound(){
Random rand = new Random();
currentCardA = deck.get(rand.nextInt(deck.size()));
currentCardB = deck.get(rand.nextInt(deck.size()));
while(!hasCommonSymbol(CurrentCardA, currenCardB)){
currentCardB = deck.get(rand.nextInt(deck.size()));

}

}

public boolean hasCommonSymbol(Card a, Card b){
for(Symbol s : a.getSymbols()){
if (b.getSymbols().contains(s)) return true;
}

return false;
}

public Card getCardA() { return currentCardA; }
public Card getCardB() { return currentCardB; }

}

