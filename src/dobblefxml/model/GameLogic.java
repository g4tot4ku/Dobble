package dobblefxml.model;

import java.util.List;
import java.util.Random;

public class GameLogic {
    private List<Card> deck;
    private Card currentCardA;
    private Card currentCardB;
    private Random rand = new Random();

    public GameLogic(List<Card> deck) {
        this.deck = deck;
        if (deck.size() < 2) {
            throw new IllegalStateException("El mazo debe tener al menos 2 cartas.");
        }
        nextRound();
    }

    public void nextRound() {
        int indexA, indexB;
        do {
            indexA = rand.nextInt(deck.size());
            indexB = rand.nextInt(deck.size());
        } while (indexA == indexB); // Aseguramos que las cartas sean diferentes

        currentCardA = deck.get(indexA);
        currentCardB = deck.get(indexB);

        // Verificamos que las cartas compartan exactamente un símbolo
        int commonSymbols = countCommonSymbols(currentCardA, currentCardB);
        if (commonSymbols != 1) {
            // Si no tienen exactamente un símbolo en común, seleccionamos otro par
            nextRound();
        }
    }

    private int countCommonSymbols(Card a, Card b) {
        int count = 0;
        for (Symbol s : a.getSymbols()) {
            if (b.getSymbols().contains(s)) {
                count++;
            }
        }
        return count;
    }

    public Card getCardA() { return currentCardA; }
    public Card getCardB() { return currentCardB; }
}