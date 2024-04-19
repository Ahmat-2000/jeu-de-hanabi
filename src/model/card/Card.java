package model.card;

public class Card {
    private CardColor color;
    private CardValue value;
    public Card(CardColor color, CardValue value) {
        this.color = color;
        this.value = value;
    }
    public CardColor getColor() {
        return color;
    }
    public void setColor(CardColor color) {
        this.color = color;
    }
    public CardValue getValue() {
        return value;
    }
    public void setValue(CardValue value) {
        this.value = value;
    }

}
