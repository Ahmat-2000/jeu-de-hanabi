package model.card;

public enum CardEnumValue {
    ONE(1), TWO(2), THREE(3),FOUR(4) ,FIVE(5);

    private final int value;

    private CardEnumValue(int value) {
    this.value = value;
    }

    public int getValue() {
    return this.value;
    }
}
