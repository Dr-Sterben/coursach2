package model;

public class Player {
    private String name; //Player
    private char symbol; //'x' or 'o'

    public Player(String name, char symbol) {
        this.name = name;
        if (symbol == 'x' || symbol == 'o')
            this.symbol = symbol;
        else
            this.symbol = 'x';
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(char symbol) {
        if (symbol == 'x' || symbol == 'o')
            this.symbol = symbol;
        else
            this.symbol = 'x';
    }
}
