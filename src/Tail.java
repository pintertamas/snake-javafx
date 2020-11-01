public class Tail extends Point{
    private int sizeX;
    private int sizeY;

    public Tail(int posX, int posY, int sizeX, int sizeY) {
        super(posX, posY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }



    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
}
