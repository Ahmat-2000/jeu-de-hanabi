package model.tocken;

public abstract class Tocken {
    protected int maxTokenValue;
    private int tockenNumber;
    public Tocken(int maxTokenValue) {
        this.tockenNumber = maxTokenValue;
        this.maxTokenValue = maxTokenValue;
    }

    public int getTockenNumber() {
        return tockenNumber;
    }
    public boolean addTocken() {
        if (tockenNumber < maxTokenValue) {
            this.tockenNumber++;
            return true;
        }
        return false;
    }
    public boolean removeTocken(){
        if(this.tockenNumber > 0) {
            this.tockenNumber--;
        }
        return isNoTockenLeft();
    }
    public boolean isNoTockenLeft(){
        return tockenNumber == 0;
    }
}
