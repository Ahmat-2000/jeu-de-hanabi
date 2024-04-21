package model.card;

public class Card {
    /** Card value */
    private CardEnumColor color;
    /** Card color */
    private CardEnumValue cardEnumValue;
    /** Hide card value and color */
    private boolean visibility;
    
    /**
     * @param color of the card
     * @param value of the card
     */
    public Card(CardEnumColor color, CardEnumValue value) {
        this.color = color;
        this.cardEnumValue = value;
        this.visibility = false;
    }


    /**
     * Get the color of the card
     * @return color of the card
     */
    public CardEnumColor getColor() {
        return color;
    }

    
    /**
     * Set the color of the card
     * @param color of the card
     */
    public void setColor(CardEnumColor color) {
        this.color = color;
    }

    /**
     * @return cardEnumValue
     */
    public CardEnumValue getCardEnumValue() {
        return cardEnumValue;
    }
    /**
     * Set the cardEnumValue of the card
     * @param cardEnumValue of the card
     */
    public void setCardEnumValue(CardEnumValue cardEnumValue) {
        this.cardEnumValue = cardEnumValue;
    }

    @Override
    public String toString() {
        return ""+this.cardEnumValue.getValue() + this.color;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
