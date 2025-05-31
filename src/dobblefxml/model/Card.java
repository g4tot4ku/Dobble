package dobblefxml.model;

import java.util.List;

/* Una carta contiene varios símbolos */
public class Card {
    private List<Symbol> symbols;

    public Card(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<Symbol> getSymbols() { return symbols; }

    public boolean hasSymbol(Symbol symbol) {
        return symbols.contains(symbol);
    }
}