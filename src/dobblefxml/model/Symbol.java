package dobblefxml.model;

import javafx.scene.image.Image;
import java.util.*;

/* Representa cada símbolo geek (nombre + imagen) */
public class Symbol {
    private String name;
    private Image image;

    public Symbol(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public String getName() { return name; }
    public Image getImage() { return image; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Symbol)) return false;
        Symbol other = (Symbol) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}