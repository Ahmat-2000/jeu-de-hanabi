package model;

public class Tocken {
    protected int maxTokenValue;
    private int tockenNumber;
    private String color;
    public Tocken(String color,int maxTokenValue) {
        this.tockenNumber = maxTokenValue;
        this.maxTokenValue = maxTokenValue;
        this.color = color;
    }

    public int getTockenNumber() {
        return tockenNumber;
    }
    public void addTocken() {
        this.tockenNumber++;
    }
    public void removeTocken(){
        this.tockenNumber--;
    }
    public boolean isNoTockenLeft(){
        return tockenNumber == 0;
    }
    public boolean isFullTocken(){
        return tockenNumber == maxTokenValue;
    }
    @Override
    public String toString() {
        return this.color + " tockens : " + this.getTockenNumber();
    }
}
