package model.card;

public enum CardValue {
    ONE(1), TWO(2), THREE(3),FOUR(4) ,FIVE(5);

    private final int value;

    private CardValue(int value) {
    this.value = value;
    }

    public int getValue() {
    return this.value;
    }
}
