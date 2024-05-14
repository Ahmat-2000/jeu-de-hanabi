package model;

public class Token {
    protected int maxTokenValue;
    private int tokenNumber;
    private String color;
    public Token(String color,int maxTokenValue) {
        this.tokenNumber = maxTokenValue;
        this.maxTokenValue = maxTokenValue;
        this.color = color;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }
    public void addToken() {
        this.tokenNumber++;
    }
    public void removeToken(){
        this.tokenNumber--;
    }
    public boolean isNoTokenLeft(){
        return tokenNumber == 0;
    }
    public boolean isFullToken(){
        return tokenNumber == maxTokenValue;
    }
    @Override
    public String toString() {
        return this.color + " tokens : " + this.getTokenNumber();
    }
}
