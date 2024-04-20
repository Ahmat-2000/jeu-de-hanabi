package model.card;

/**
 * Enumeation for card values
 */
public enum CardEnumValue {
    ONE(1), TWO(2), THREE(3),FOUR(4) ,FIVE(5);

    /** card value */
    private final int value;

    /**
     * private constructor, it called by the compiler
     * @param value of a card
     */
    private CardEnumValue(int value) {
        this.value = value;
    }

    /**
     * @return value of a card
     */
    public int getValue() {
        return this.value;
    }
}
