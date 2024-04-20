package model.card;

public class Card {
    private CardEnumColor color;
    private CardEnumValue cardEnumValue;
    
    public Card(CardEnumColor color, CardEnumValue value) {
        this.color = color;
        this.cardEnumValue = value;
    }
    public CardEnumColor getColor() {
        return color;
    }
    public void setColor(CardEnumColor color) {
        this.color = color;
    }

    public CardEnumValue getCardEnumValue() {
        return cardEnumValue;
    }
    public void setCardEnumValue(CardEnumValue cardEnumValue) {
        this.cardEnumValue = cardEnumValue;
    }
}
